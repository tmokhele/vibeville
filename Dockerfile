FROM ubuntu:15.04

RUN mkdir /app
WORKDIR /app

COPY target/tebogochat.jar tebogochat.jar
EXPOSE 8080

CMD ["java", "-jar", "tebogochat.jar"]
