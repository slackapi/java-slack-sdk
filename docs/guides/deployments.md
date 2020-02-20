---
layout: default
title: "Deployments"
lang: en
---

# Deployments

It's about time to deploy your brilliant Slack apps to the production environment. As you know, there are so many choices. In this guide, we'll cover the following infrastructure.

* Heroku
* Google Cloud Run
* AWS Fargate/ECS

Needless to say, it doesn't mean Bolt apps work only on those. If you have better options, send us the pull requests to share your brain with others in the community!

---

## Classic App Hosting

TODO

### Heroku

TODO

### Google App Engine

TODO

### AWS Elsatic Beanstalk

TODO

---

## Container Services

In these days, running applications as [Docker](https://www.docker.com/) container processes is much more common than before. Packaging and building Docker images for Bolt apps don't take long at all.

#### build.gradle

Create a jar file with all the runtime dependencies.

```groovy
configurations {
  jar.archiveName = 'slack-app.jar'
}
jar {
  manifest {
    attributes 'Main-Class': 'hello.MyApp'
  }
  from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
}
```

#### Dockerfile

Here is a `Dockerfile` to run multi-stage builds.

```dockerfile
FROM gradle:5.6.3-jdk11 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM gcr.io/distroless/java:11
COPY --from=build /home/gradle/src/build/libs /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/slack-app.jar"]
```

Then, you can build an image and run container processes using the image below.

```bash
export SLACK_SIGNING_SECRET=abc123***
export SLACK_BOT_TOKEN=xoxb-***

# Build a Docker image
docker build -t your-repo/slack-app .

# Start a new Docker container process
docker run -p 3000:3000 -it your-repo/slack-app \
  -e \
  SLACK_SIGNING_SECRET=$SLACK_SIGNING_SECRET \
  SLACK_BOT_TOKEN=$SLACK_BOT_TOKEN
```

Are you interested in deploying the app to cloud container services (e.g., AWS Fargate/ECS, Google Cloud Run, etc.)? Check the [Deployments Guide]({{ site.url | append: site.baseurl }}/guides/deployments) for further information.

### Google Cloud Run

TODO

### AWS Fargate/ECS

TODO
