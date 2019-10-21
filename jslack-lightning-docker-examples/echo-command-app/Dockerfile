FROM gradle:5.6.3-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM gcr.io/distroless/java:11
COPY --from=build /home/gradle/src/build/libs /app
EXPOSE 8080
ENTRYPOINT ["java",  "-jar", "/app/slack-app-backend.jar"]
