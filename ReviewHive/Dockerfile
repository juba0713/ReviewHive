FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml for dependency resolution
COPY pom.xml mvnw* .mvn/ ./

# Download dependencies (use a cache layer)
RUN ./mvnw dependency:go-offline

# Copy the rest of the application source
COPY ./ ./

# Build the application
RUN ./mvnw clean package -DskipTests

# Extract the built JAR file name dynamically
RUN cp target/*.jar reviewhive.jar

# Use a minimal JDK runtime image for running the app
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=0 /app/reviewhive.jar reviewhive.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "reviewhive.jar"]
