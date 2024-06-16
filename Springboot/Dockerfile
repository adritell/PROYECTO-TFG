# Compilación
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /usr/src/project



# Verificar si existe la carpeta target y borrarla si es necesario
RUN test -d "target" && RUN rm -rf target || true

# Copiar el resto de los archivos del proyecto
COPY . .

# Compilar el proyecto con Maven
RUN mvn package -DskipTests

# Ejecución
FROM openjdk:17-jdk-alpine
VOLUME /tmp

# Copiar el archivo JAR generado desde la etapa anterior
COPY --from=builder /usr/src/project/target/*.jar /app.jar

# Punto de entrada para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
