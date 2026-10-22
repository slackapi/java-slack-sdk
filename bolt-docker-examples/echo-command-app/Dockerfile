FROM gradle:8-jdk21 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# gcr.io/distroless/java:11 is deprecated and based on Debian 11 (bullseye), which
# has no fixed ca-certificates package (eMudhra 53119). java21-debian13:latest,
# pinned to the digest uploaded 2026-10-21 (ships ca-certificates 20250419+deb13u1).
FROM gcr.io/distroless/java21-debian13@sha256:81093f54a674ffc9bcc1ba5b8fe75158bf6c56e592ec6513b1603a0bda485388
COPY --from=build /home/gradle/src/build/libs /app
EXPOSE 8080
ENTRYPOINT ["java",  "-jar", "/app/slack-app-backend.jar"]
