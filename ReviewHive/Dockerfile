# Step 1: Build the Maven project
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app  
COPY ReviewHive/pom.xml .  
RUN mvn dependency:go-offline  
COPY ReviewHive/. . 
RUN mvn clean package -DskipTests  

# Step 2: Create the runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app  
COPY --from=build /app/target/ReviewHive-0.0.1-SNAPSHOT.jar ReviewHive.jar  
EXPOSE 8080
ENTRYPOINT ["java","-jar","ReviewHive.jar"]
