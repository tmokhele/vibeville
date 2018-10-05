FROM openjdk:8-jdk-alpine

RUN mkdir /app
WORKDIR /app

COPY target/tebogochat-1.0-SNAPSHOT.jar tebogochat.jar
EXPOSE 8080

CMD ["java", "-jar", "tebogochat.jar"]
