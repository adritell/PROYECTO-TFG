# Etapa de compilación
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /usr/src/project

# Copiar los archivos necesarios para la compilación
COPY pom.xml ./
COPY src ./src

# Construir el JAR de la aplicación
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Crear un volumen temporal para almacenamiento
VOLUME /tmp

# Copiar el JAR generado desde la etapa de compilación
COPY --from=builder /usr/src/project/target/*.jar app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
