FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . . 
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/ReviewHive-0.0.1-SNAPSHOT.jar ReviewHive.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ReviewHive.jar"]
