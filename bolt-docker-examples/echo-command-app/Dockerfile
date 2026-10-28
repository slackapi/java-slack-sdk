FROM gradle:8-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM gcr.io/distroless/java17-debian13
COPY --from=build /home/gradle/src/build/libs /app
EXPOSE 8080
ENTRYPOINT ["java",  "-jar", "/app/slack-app-backend.jar"]
