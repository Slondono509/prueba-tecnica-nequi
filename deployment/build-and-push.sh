#!/bin/bash

# Variables
AWS_REGION="us-east-1"
PROJECT_NAME="nequi-prueba"
ENVIRONMENT="dev"
ECR_REPOSITORY="${PROJECT_NAME}-${ENVIRONMENT}"

# Verificar que AWS_ACCOUNT_ID esté configurado
if [ -z "$AWS_ACCOUNT_ID" ]; then
    echo "Error: La variable AWS_ACCOUNT_ID no está configurada"
    echo "Por favor, configúrala con: export AWS_ACCOUNT_ID=\$(aws sts get-caller-identity --query Account --output text)"
    exit 1
fi

# Verificar que aws cli esté instalado
if ! command -v aws &> /dev/null; then
    echo "Error: aws cli no está instalado"
    echo "Por favor, instálalo siguiendo las instrucciones en: https://aws.amazon.com/cli/"
    exit 1
fi

# Verificar que docker esté instalado y corriendo
if ! docker info &> /dev/null; then
    echo "Error: docker no está instalado o no está corriendo"
    echo "Por favor, verifica la instalación de docker"
    exit 1
fi

echo "Iniciando proceso de build y push de la imagen Docker..."

# Obtener el login token para ECR
echo "Autenticando con ECR..."
aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

# Verificar si el repositorio existe, si no, crearlo
if ! aws ecr describe-repositories --repository-names ${ECR_REPOSITORY} --region ${AWS_REGION} &> /dev/null; then
    echo "Creando repositorio ECR ${ECR_REPOSITORY}..."
    aws ecr create-repository --repository-name ${ECR_REPOSITORY} --region ${AWS_REGION}
fi

# Obtener la URL del repositorio ECR
ECR_REPOSITORY_URI=$(aws ecr describe-repositories --repository-names ${ECR_REPOSITORY} --region ${AWS_REGION} --query 'repositories[0].repositoryUri' --output text)

# Construir la imagen Docker
echo "Construyendo la imagen Docker..."
docker build -t ${ECR_REPOSITORY}:latest -f deployment/Dockerfile .

# Etiquetar la imagen
echo "Etiquetando la imagen..."
docker tag ${ECR_REPOSITORY}:latest ${ECR_REPOSITORY_URI}:latest
# También etiquetar con timestamp para mantener historial
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
docker tag ${ECR_REPOSITORY}:latest ${ECR_REPOSITORY_URI}:${TIMESTAMP}

# Subir las imágenes al ECR
echo "Subiendo la imagen a ECR..."
docker push ${ECR_REPOSITORY_URI}:latest
docker push ${ECR_REPOSITORY_URI}:${TIMESTAMP}

echo "¡Proceso completado exitosamente!"
echo "Las imágenes han sido subidas a:"
echo "- ${ECR_REPOSITORY_URI}:latest"
echo "- ${ECR_REPOSITORY_URI}:${TIMESTAMP}" 