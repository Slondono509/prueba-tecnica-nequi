# Microservicio de Gestión de Franquicias, Sucursales y Productos

## Descripción
Este microservicio implementa una arquitectura limpia (Clean Architecture) para gestionar franquicias, sus sucursales y productos asociados. Utiliza Spring Boot con programación reactiva y R2DBC para la persistencia de datos.

## Estructura del Proyecto
El proyecto sigue los principios de Clean Architecture con la siguiente estructura:

```
├── applications/          # Configuración de la aplicación
├── domain/               # Capa de dominio
│   ├── model/           # Modelos de dominio
│   └── usecase/         # Casos de uso
├── infrastructure/       # Capa de infraestructura
│   ├── driven-adapters/ # Adaptadores dirigidos
│   │   └── r2dbc-postgresql/
│   │       ├── src/
│   │       │   ├── main/
│   │       │   │   ├── java/co/com/nequi/r2dbc/
│   │       │   │   │   ├── branch/      # Adaptadores para sucursales
│   │       │   │   │   ├── franchise/   # Adaptadores para franquicias
│   │       │   │   │   ├── product/     # Adaptadores para productos
│   │       │   │   │   └── entity/      # Entidades de base de datos
│   │       │   └── test/                # Pruebas unitarias
│   ├── entry-points/    # Puntos de entrada
│   │   └── reactive-web/# API REST reactiva
│   └── helpers/         # Utilidades y helpers
```

## Tecnologías Principales
- Java 17
- Spring Boot 3.x
- Spring WebFlux
- R2DBC (PostgreSQL)
- Project Reactor
- Gradle
- JUnit 5
- Mockito
- Lombok

## API REST

### Franquicias (Franchises)
Base URL: `/api/franchises`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/` | Crear una nueva franquicia |
| GET | `/` | Obtener todas las franquicias |
| GET | `/{id}` | Obtener una franquicia por ID |
| PUT | `/{id}` | Actualizar una franquicia |
| DELETE | `/{id}` | Eliminar una franquicia |
| GET | `/{franchiseId}/branches` | Obtener sucursales de una franquicia |
| GET | `/{franchiseId}/highest-stock-products` | Obtener productos con mayor stock por franquicia |

#### Ejemplos de Peticiones

##### Crear Franquicia (POST /)
```json
{
  "name": "Mi Franquicia"
}
```

##### Actualizar Franquicia (PUT /{id})
```json
{  
  "name": "Mi Franquicia Actualizada"
}
```

### Sucursales (Branches)
Base URL: `/api/branches`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/` | Crear una nueva sucursal |
| GET | `/` | Obtener todas las sucursales |
| GET | `/{id}` | Obtener una sucursal por ID |
| PUT | `/{id}` | Actualizar una sucursal |
| DELETE | `/{id}` | Eliminar una sucursal |
| GET | `/{branchId}/products` | Obtener productos de una sucursal |

#### Ejemplos de Peticiones

##### Crear Sucursal (POST /)
```json
{
  "name": "Sucursal Centro",
  "franchiseId": 1
}
```

##### Actualizar Sucursal (PUT /{id})
```json
{  
  "name": "Sucursal Centro Actualizada",
  "franchiseId": 1
}
```

### Productos (Products)
Base URL: `/api/products`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/` | Crear un nuevo producto |
| GET | `/` | Obtener todos los productos |
| GET | `/{id}` | Obtener un producto por ID |
| PUT | `/{id}` | Actualizar un producto |
| DELETE | `/{id}` | Eliminar un producto |
| PATCH | `/{id}/stock` | Actualizar el stock de un producto |

#### Ejemplos de Peticiones

##### Crear Producto (POST /)
```json
{
  "name": "Producto Nuevo",
  "stock": 100,
  "branchId": 1
}
```

##### Actualizar Producto (PUT /{id})
```json
{  
  "name": "Producto Actualizado",
  "stock": 150,
  "branchId": 1
}
```

##### Actualizar Stock de Producto (PATCH /{id}/stock)
```json
50
```

### Formatos de Respuesta
Todas las respuestas son en formato JSON y siguen la siguiente estructura:

#### Respuesta Exitosa
```json
{
  "id": 1,
  "name": "Ejemplo",
  "createdAt": "2024-03-14T12:00:00Z",
  "updatedAt": "2024-03-14T12:00:00Z"
}
```

#### Respuesta de Error
```json
{
  "error": "Descripción del error",
  "status": 400,
  "timestamp": "2024-03-14T12:00:00Z"
}
```

### Códigos de Estado HTTP
- 200 OK: Petición exitosa
- 201 Created: Recurso creado exitosamente
- 204 No Content: Eliminación exitosa
- 400 Bad Request: Error en la petición
- 404 Not Found: Recurso no encontrado
- 500 Internal Server Error: Error del servidor

## Capas de la Aplicación

### Domain
- **Model**: Define las entidades core del negocio y sus reglas
- **Usecase**: Implementa la lógica de negocio y casos de uso

### Infrastructure
- **Driven Adapters**: 
  - Implementación de repositorios con R2DBC
  - Adaptadores para persistencia de datos
  - Transformación entre entidades y modelos de dominio
- **Entry Points**: 
  - API REST reactiva
  - Controladores para gestión de recursos
  - Manejo de peticiones y respuestas

## Patrones Implementados
- Clean Architecture
- Dependency Injection
- Repository Pattern
- Adapter Pattern
- Gateway Pattern
- Builder Pattern

## Testing
El proyecto incluye pruebas unitarias exhaustivas para cada capa:


## Configuración y Despliegue

### Requisitos Previos
- Java 17 o superior
- PostgreSQL 12 o superior
- Gradle 7.x

### Variables de Entorno

en el archivo 

Ruta: applications/app-service/src/main/resources/application.yaml

Configurar el valor para las variables

```properties
SPRING_R2DBC_URL=r2dbc:postgresql://localhost:5432/db_name
SPRING_R2DBC_USERNAME=username
SPRING_R2DBC_PASSWORD=password
```

### Construcción
```bash
./gradlew clean build
```





