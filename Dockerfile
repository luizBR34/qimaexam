FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/exam-0.0.1-SNAPSHOT.jar /app/exam-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/exam-0.0.1-SNAPSHOT.jar"]
