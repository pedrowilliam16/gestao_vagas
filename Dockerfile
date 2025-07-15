# Etapa de build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa de runtime
FROM openjdk:17-jdk-slim

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/gestao_vagas-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
