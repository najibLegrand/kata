# syntax=docker/dockerfile:1

# --- Build (Maven) ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache des dépendances
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# Code
COPY src ./src
RUN mvn -q -B -DskipTests package

# --- Runtime (JRE only) ---
FROM eclipse-temurin:21-jre
WORKDIR /app
# copie le JAR généré
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
