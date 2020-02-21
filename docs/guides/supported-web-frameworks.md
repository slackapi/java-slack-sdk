---
layout: default
title: "Supported Web Frameworks"
lang: en
---

# Supported Web Frameworks

A Bolt app doesn't depend on any specific libraries and frameworks.

It works on Servlet containers out-of-the-box. So, developers can run Bolt apps with most Web frameworks on the JVM. **SlackAppServlet** is a simple Servlet that receives HTTP requests coming to `POST /slack/events` URI and properly dispatches each request to corresponding handlers in a Bolt app.

Even running Bolt apps on non-Servlet settings like [Micronaut](https://micronaut.io/) is feasible if there is an adapter that transforms its specific HTTP interpretation to Bolt interfaces.

## Supported Frameworks

In this section, I'll share some minimum working examples for the following popular frameworks.

* [Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Micronaut](https://micronaut.io/)
* [Quarkus](https://quarkus.io/)

## Spring Boot

[Spring Boot](https://spring.io/guides/gs/spring-boot/) is the most popular Web framework in Java. Enabling **SlackAppServlet** in your Spring Boot application is the easiest way to run Bolt apps with the framework. Let's look at a tiny Gradle project.

#### build.gradle

Let's start with putting `build.gradle` file in the root directory of your project. As you see, this is just a simple and straight-forward Spring Boot app configuration.

```groovy
plugins {
  id 'org.springframework.boot' version '2.2.2.RELEASE'
  id 'io.spring.dependency-management' version '1.0.8.RELEASE'
  id 'java'
}
group = 'com.example'
version = '0.0.1-SNAPSHOT'
repositories {
  mavenCentral()
  mavenLocal()
}
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'com.slack.api:bolt:{{ site.sdkLatestVersion }}'
}
```

#### src/main/java/hello/SlackApp.java

This is an essential part of this application. All the logic to handle Slack events should be here. In this `@Configuration` class, you can also inject service classes and whatever into Bolt's **App** by taking full advantage of the Spring DI container.

```java
package hello;

import com.slack.api.bolt.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {
  @Bean
  public App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack(r -> r.text("Thanks!"));
    });
    return app;
  }
}
```

#### src/main/java/hello/SlackAppController.java

This is a kind of boilerplate code to add an endpoint. You can customize the path by modifying the argument of `@WebServlet` annotation.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
  public SlackAppController(App app) {
    super(app);
  }
}
```

#### src/main/java/hello/Application.java

This is also a boilerplate code. It just enables Spring's component scan and bootstraps a Spring Boot application.

```java
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

#### src/main/resources/application.yml

Lastly, place a configuration file in the resources directory. The following example customizes the log level only for **Slack SDK for Java** and the port to listen from 8080 to 3000.

```yaml
logging.level:
  com.slack.api: DEBUG
server:
  port: 3000
```

### Boot the Bolt App

That's all set! It's time to hit `gradle bootRun` to boot the app.


```
$ gradle bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2020-01-17 10:22:50.011  INFO 7815 --- [main] hello.Application                        : Starting Application on MACHNE_NAME with PID 7815 (/path-to-project/build/classes/java/main started by seratch in /path-to-project)
2020-01-17 10:22:50.013  INFO 7815 --- [main] hello.Application                        : No active profile set, falling back to default profiles: default
2020-01-17 10:22:50.473  INFO 7815 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 3000 (http)
2020-01-17 10:22:50.478  INFO 7815 --- [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-01-17 10:22:50.479  INFO 7815 --- [main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.29]
2020-01-17 10:22:50.517  INFO 7815 --- [main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-01-17 10:22:50.517  INFO 7815 --- [main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 478 ms
2020-01-17 10:22:50.750  INFO 7815 --- [main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-01-17 10:22:50.837  INFO 7815 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 3000 (http) with context path ''
2020-01-17 10:22:50.839  INFO 7815 --- [main] hello.Application                        : Started Application in 1.079 seconds (JVM running for 1.301)
<=========----> 75% EXECUTING [17s]
> :bootRun
```

Then, forward requests to the app as always.

```bash
ngrok http 3000 --subdomain {your-favorite-one}
```


## Micronaut

TODO 

If you prefer [Micronaut](https://micronaut.io/) rather than commonplace Servlet environments, add **Bolt-micronaut**, not **Bolt-jetty**.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-micronaut</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

#### Application.java

```java
import io.micronaut.runtime.Micronaut;

public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
```

#### AppFactory.java

```java
import com.slack.api.bolt.App;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class AppFactory {
  @Singleton
  public App createApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack(r -> r.text("Thanks!"));
    });
    return app;
  }
}
```


## Quarkus

TODO
