FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests -B

RUN mvn -f user-service/pom.xml clean package -DskipTests -B spring-boot:repackage

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/user-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]