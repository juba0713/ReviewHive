FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

COPY ReviewHive/pom.xml .

# Download dependencies (use a cache layer)
RUN mvn dependency:go-offline  

# Copy the rest of the application source
COPY ReviewHive/. . 

# Build the application
RUN mvn clean package -DskipTests  

# Use a minimal JDK runtime image for running the app
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/ReviewHive-0.0.1-SNAPSHOT.jar ReviewHive.jar  

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "reviewhive.jar"]
