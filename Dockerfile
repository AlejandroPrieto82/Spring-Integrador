# Etapa 1: construir la app con Maven y Java 17
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Dar permisos a mvnw por si se usa
RUN chmod +x mvnw

# Construir el proyecto, omitiendo tests
RUN ./mvnw clean install -DskipTests

# Etapa 2: imagen ligera para ejecutar la app
FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/target/health-0.0.1-SNAPSHOT.jar ./health.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "health.jar"]
