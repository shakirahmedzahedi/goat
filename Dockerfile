FROM openjdk:21-jdk-slim as builder

WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

RUN chmod +x mvnw
COPY src ./src

RUN ./mvnw clean package

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/goat-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]