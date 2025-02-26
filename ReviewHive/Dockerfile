# Step 1: Build the Maven project
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app

# Copy only pom.xml first to leverage caching for dependencies
COPY ReviewHive/pom.xml .
RUN mvn dependency:resolve 

# Copy the full project source and build
COPY ReviewHive/. .
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar  

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
