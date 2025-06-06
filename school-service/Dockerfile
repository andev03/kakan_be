# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

# Tải dependencies và cache chúng
RUN mvn dependency:go-offline -B

# Copy source code và build
COPY src ./src

RUN mvn clean package -DskipTests -B spring-boot:repackage

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy file JAR từ stage build
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]