# syntax=docker/dockerfile:1
FROM openjdk:16-alpine3.13 as buildApp
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

RUN ./gradlew clean build -x test



FROM openjdk:16-alpine3.13
COPY --from=buildApp /app/build/libs/*1.jar myApp.jar
ENTRYPOINT ["java","-jar","/myApp.jar"]
