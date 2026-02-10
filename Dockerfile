# Use official OpenJDK image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Run the jar
CMD ["sh", "-c", "java -jar target/*.jar --server.port=$PORT"]
