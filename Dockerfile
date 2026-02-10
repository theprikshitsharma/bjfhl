# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /build

# Copy entire bfhl-api project
COPY bfhl-api ./bfhl-api

# Move into project directory
WORKDIR /build/bfhl-api

# Build the jar
RUN mvn clean package -DskipTests


# ---------- Run Stage ----------
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy jar from build stage
COPY --from=build /build/bfhl-api/target/*.jar app.jar

# Render provides PORT environment variable
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
