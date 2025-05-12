# Build stage
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /workspace/app

# Configurar variables de entorno para Gradle
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true"
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copiar archivos de gradle primero para aprovechar la caché de capas
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .
COPY gradle.properties .
COPY main.gradle .

# Dar permisos de ejecución al gradlew
RUN chmod +x gradlew

# Verificar la estructura del proyecto
RUN ./gradlew projects --no-daemon

# Descargar dependencias
RUN ./gradlew --no-daemon dependencies

# Copiar el código fuente
COPY . .

# Listar contenido para debug
RUN ls -la && ls -la applications/

# Construir el proyecto
RUN ./gradlew clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Instalar dependencias y configurar usuario
RUN apt-get update \
    && apt-get upgrade -y \
    && apt-get install --no-install-recommends -y curl \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && addgroup --system appgroup \
    && adduser --system appuser \
    && usermod -a -G appgroup appuser

# Configurar zona horaria
ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Copiar el JAR desde el stage de build
COPY --from=build /workspace/app/applications/app-service/build/libs/*.jar app.jar
RUN chown appuser:appgroup /app/app.jar

# Configurar variables de entorno y usuario
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
USER appuser

# Exponer puerto y configurar health check
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]