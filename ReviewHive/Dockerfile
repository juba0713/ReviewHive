# Step 1: Build the Maven project
FROM maven:3.9.6-eclipse-temurin AS build  # Correct image name
WORKDIR /app  
COPY ReviewHive/pom.xml .  
RUN mvn dependency:go-offline  
COPY ReviewHive/. .  
RUN mvn clean package -DskipTests  

# Step 2: Create the runtime image
FROM eclipse-temurin:21-jdk-slim  # Use a valid OpenJDK 21 runtime
WORKDIR /app  
COPY --from=build /app/target/ReviewHive-0.0.1-SNAPSHOT.jar ReviewHive.jar  
EXPOSE 8080
ENTRYPOINT ["java","-jar","ReviewHive.jar"]
