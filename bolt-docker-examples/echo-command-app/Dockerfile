FROM gradle:8-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# gcr.io/distroless/java:11 is deprecated and based on Debian 11 (bullseye), which
# has no fixed ca-certificates package (eMudhra 53119). java17-debian13:latest,
# pinned to the digest uploaded 2026-10-21 (ships ca-certificates 20250419+deb13u1).
FROM gcr.io/distroless/java17-debian13@sha256:bc5ad38faf0501a7f60859f85f00cfb285639736e2e948a2d98262f5b022a934
COPY --from=build /home/gradle/src/build/libs /app
EXPOSE 8080
ENTRYPOINT ["java",  "-jar", "/app/slack-app-backend.jar"]
