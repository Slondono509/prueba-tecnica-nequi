#!/bin/bash

# Función para imprimir mensajes de diagnóstico
print_debug() {
    echo "==> $1"
}

# Verificar AWS CLI y credenciales
print_debug "Verificando AWS CLI..."
if ! which aws > /dev/null 2>&1; then
    echo "Error: aws cli no está en el PATH"
    exit 1
fi

print_debug "Verificando credenciales de AWS..."
if ! aws sts get-caller-identity &> /dev/null; then
    echo "Error: No hay credenciales de AWS configuradas o son inválidas"
    echo "Por favor, ejecuta 'aws configure' para configurar tus credenciales"
    exit 1
fi

# Variables
AWS_REGION="us-east-1"
PROJECT_NAME="nequi-prueba"
ENVIRONMENT="dev"
ECR_REPOSITORY="${PROJECT_NAME}-${ENVIRONMENT}"

# Verificar que AWS_ACCOUNT_ID esté configurado
print_debug "Obteniendo AWS Account ID..."
AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
if [ -z "$AWS_ACCOUNT_ID" ]; then
    echo "Error: No se pudo obtener el AWS Account ID"
    exit 1
fi
print_debug "AWS Account ID: $AWS_ACCOUNT_ID"

# Verificar que docker esté instalado y corriendo
print_debug "Verificando Docker..."
if ! docker info &> /dev/null; then
    echo "Error: docker no está instalado o no está corriendo"
    exit 1
fi

print_debug "Iniciando proceso de build y push de la imagen Docker..."

# Obtener el login token para ECR
print_debug "Autenticando con ECR..."
if ! aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com; then
    echo "Error: No se pudo autenticar con ECR"
    exit 1
fi

# Verificar si el repositorio existe, si no, crearlo
print_debug "Verificando repositorio ECR..."
if ! aws ecr describe-repositories --repository-names ${ECR_REPOSITORY} --region ${AWS_REGION} &> /dev/null; then
    print_debug "Creando repositorio ECR ${ECR_REPOSITORY}..."
    if ! aws ecr create-repository --repository-name ${ECR_REPOSITORY} --region ${AWS_REGION}; then
        echo "Error: No se pudo crear el repositorio ECR"
        exit 1
    fi
fi

# Obtener la URL del repositorio ECR
ECR_REPOSITORY_URI=$(aws ecr describe-repositories --repository-names ${ECR_REPOSITORY} --region ${AWS_REGION} --query 'repositories[0].repositoryUri' --output text)
print_debug "URL del repositorio ECR: ${ECR_REPOSITORY_URI}"

# Construir la imagen Docker
print_debug "Construyendo la imagen Docker..."
if ! docker build -t ${ECR_REPOSITORY}:latest -f deployment/Dockerfile .; then
    echo "Error: Fallo en el build de Docker"
    exit 1
fi

# Etiquetar la imagen
print_debug "Etiquetando la imagen..."
docker tag ${ECR_REPOSITORY}:latest ${ECR_REPOSITORY_URI}:latest
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
docker tag ${ECR_REPOSITORY}:latest ${ECR_REPOSITORY_URI}:${TIMESTAMP}

# Subir las imágenes al ECR
print_debug "Subiendo la imagen a ECR..."
if ! docker push ${ECR_REPOSITORY_URI}:latest; then
    echo "Error: No se pudo subir la imagen latest"
    exit 1
fi

if ! docker push ${ECR_REPOSITORY_URI}:${TIMESTAMP}; then
    echo "Error: No se pudo subir la imagen con timestamp"
    exit 1
fi

print_debug "¡Proceso completado exitosamente!"
echo "Las imágenes han sido subidas a:"
echo "- ${ECR_REPOSITORY_URI}:latest"
echo "- ${ECR_REPOSITORY_URI}:${TIMESTAMP}" 