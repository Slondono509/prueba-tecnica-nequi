# Build stage
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /workspace/app

# Copiar el código fuente y construir
COPY . .
RUN ./gradlew clean :applications:app-service:build -x test

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
COPY --from=build /workspace/app/applications/app-service/build/libs/pruebaTecnica.jar app.jar
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