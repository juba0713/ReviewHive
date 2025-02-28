FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app  
COPY ReviewHive/pom.xml .  
RUN mvn dependency:go-offline  
COPY ReviewHive/. . 
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /app/target/ReviewHive-0.0.1-SNAPSHOT.jar ReviewHive-1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ReviewHive.jar"]
