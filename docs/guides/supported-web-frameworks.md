---
layout: default
title: "Supported Web Frameworks"
lang: en
---

# Supported Web Frameworks

Bolt for Java doesn't depend on any specific environments and frameworks.

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
  id 'org.springframework.boot' version '{{ site.springBootVersion }}'
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
      return ctx.ack("What's up?");
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
 :: Spring Boot ::        (v{{ site.springBootVersion }})

[main] hello.Application                        : Starting Application on MACHNE_NAME with PID 7815 (/path-to-project/build/classes/java/main started by seratch in /path-to-project)
[main] hello.Application                        : No active profile set, falling back to default profiles: default
[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 3000 (http)
[main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.29]
[main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
[main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 478 ms
[main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 3000 (http) with context path ''
[main] hello.Application                        : Started Application in 1.079 seconds (JVM running for 1.301)
<=========----> 75% EXECUTING [17s]
> :bootRun
```

Then, forward requests to the app as always.

```bash
ngrok http 3000 --subdomain {your-favorite-one}
```

---

## Micronaut

If you prefer [Micronaut](https://micronaut.io/) rather than commonplace Servlet environments, add **bolt-micronaut**, NOT **bolt-jetty**. Needless to say, that's the same for Gradle projects.

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

#### src/main/java/hello/Application.java

**Application.java** is a kind of boilerplate. You can cut and paste the following code as-is.

```java
package hello;

import io.micronaut.runtime.Micronaut;

public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
```

#### src/main/java/hello/AppFactory.java

The simplest way would be to have some code that initializes the **App** instance in a factory class. Micronaut scans the classes with DI related annotations and uses them when injecting components for you.

```java
package hello;

import com.slack.api.bolt.App;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class AppFactory {

  @Singleton
  public AppConfig createAppConfig() {
    return new AppConfig(); // loads the env variables
  }

  @Singleton
  public App createApp(AppConfig config) {
    App app = new App(config);
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### src/main/resources/application.yml

To use a different port like 3000, place a configuration file for the app as below.

```yaml
---
micronaut:
  application:
    name: micronaut-slack-app
  server:
    port: 3000
```

### Start the Micronaut App

That's all set! It's time to hit `mvn run` to boot the app.

```
[main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 1321ms. Server Running: http://localhost:3000
```

---


## Quarkus

[Quarkus](https://quarkus.io/) is a Web application framework that supports packaging for GraalVM and HotSpot. In this section, I'll explain how to configure SlackAppServlet with the framework.

You can generate a blank project from [code.quarkus.io](https://code.quarkus.io/). For simple Bolt apps, we recommend using **Undertow Servlet** in the **Web** component section. Nothing else is required. Just click **Generate your application** button and download the generated zip file.

Although the RESTEasy dependency has to be included in a blank project, it's not necessary for a Bolt app. You can manually remove them from the build dependencies.

### pom.xml

```xml
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-core</artifactId>
  <version>${quarkus.version}</version>
</dependency>
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-undertow</artifactId>
  <version>${quarkus.version}</version>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

### src/main/java/hello/SlackApp.java

The only thing you need to do is to create a `@WebServlet`-wired class. The Quarkus framework scans such classes and enables them for you.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/slack/events")
public class SlackApp extends SlackAppServlet {
  private static final long serialVersionUID = 1L;
  public SlackApp() throws IOException { super(initSlackApp()); }
  public SlackApp(App app) { super(app); }

  private static App initSlackApp() throws IOException {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### src/main/kotlin/hello/SlackApp.java

If you choose Kotlin language when generating the project, the code would like this:

```kotlin
package hello

import com.slack.api.bolt.App
import com.slack.api.bolt.servlet.SlackAppServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/slack/events")
class SlackApp : SlackAppServlet(initSlackApp()) {

  companion object {
    fun initSlackApp(): App {
      val app = App()
      app.command("/ping") { req, ctx ->
        ctx.ack("<@${req.payload.userId}> pong!")
      }
      return app
    }
  }
}
```

#### src/mian/kotlin/app.kt

For properly using Dependency Injection, having producers may be better.

```kotlin
package hello

import com.slack.api.bolt.App
import com.slack.api.bolt.servlet.SlackAppServlet
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.servlet.annotation.WebServlet

@WebServlet("/slack/events")
class SlackApp(app: App?) : @Inject SlackAppServlet(app)

class Components {
  @Produces
  fun initApp(): App {
    val app = App()
    app.command("/ping") { req, ctx ->
      ctx.ack("<@${req.payload.userId}> pong!")
    }
    return app
  }
}
```

### src/main/resources/application.properties

The default port Quarkus uses is 8080. You can change the port by having the following config.

```
quarkus.http.port=3000
```

### Run the App

That’s all set! It’s time to run the app in its the development mode.

```bash
./mvnw quarkus:dev
```

If your Quarkus project is correctly configured, the stdout should look like this.

```
[INFO] --- quarkus-maven-plugin:{{ site.quarkusVersion }}:dev (default-cli) @ code-with-quarkus ---
[INFO] Applied plugin: 'all-open'
[INFO] Changes detected - recompiling the module!
Listening for transport dt_socket at address: 5005
[io.quarkus] (main) code-with-quarkus 1.0.0-SNAPSHOT (running on Quarkus {{ site.quarkusVersion }}) started in 0.902s. Listening on: http://0.0.0.0:3000
[io.quarkus] (main) Profile dev activated. Live Coding activated.
[io.quarkus] (main) Installed features: [cdi, kotlin, servlet]
```

The hot reload mode is enabled by default.

```
[io.qua.dev] (vert.x-worker-thread-2) Changed source files detected, recompiling [/path-to-project/src/main/kotlin/hello/SlackApp.kt]
[io.quarkus] (vert.x-worker-thread-2) Quarkus stopped in 0.001s
[io.quarkus] (vert.x-worker-thread-2) code-with-quarkus 1.0.0-SNAPSHOT (running on Quarkus {{ site.quarkusVersion }}) started in 0.163s. Listening on: http://0.0.0.0:3000
[io.quarkus] (vert.x-worker-thread-2) Profile dev activated. Live Coding activated.
[io.quarkus] (vert.x-worker-thread-2) Installed features: [cdi, kotlin, servlet]
[io.qua.dev] (vert.x-worker-thread-2) Hot replace total time: 0.572s
```