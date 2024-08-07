---
lang: en
---

# Supported Web Frameworks

Bolt for Java doesn't depend on any specific environments and frameworks.

It works on Servlet containers out-of-the-box. So, developers can run Bolt apps with most Web frameworks on the JVM. **SlackAppServlet** is a simple Servlet that receives HTTP requests coming to `POST /slack/events` URI and properly dispatches each request to corresponding handlers in a Bolt app.

Even running Bolt apps on non-Servlet settings like [Micronaut](https://micronaut.io/) and [Helidon](https://helidon.io/) is feasible if there is an adapter that transforms its specific HTTP interpretation to Bolt interfaces.

---
## Supported Frameworks

Below are some minimum working examples for the following popular frameworks.

* [Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Micronaut](https://micronaut.io/)
* [Quarkus Undertow](https://quarkus.io/)
* [Helidon SE](https://helidon.io/)

---
## Spring Boot

[Spring Boot](https://spring.io/guides/gs/spring-boot/) is the most popular Web framework in Java. Enabling `SlackAppServlet` in your Spring Boot application is the easiest way to run Bolt apps with the framework. Let's look at a tiny Gradle project. **Please note that Bolt properly works with Spring Boot 2.2 or newer versions.**
Also, when you add Spring Boot extensions such as Spring Security, the app may not work well with Bolt.
In that case, please consider splitting the app into a few and make the Bolt app as simple as possible.

### `build.gradle`

Let's start with putting `build.gradle` file in the root directory of your project. Here is a simple project setting with Spring Boot 3. As you see, this is just a simple and straight-forward Spring Boot app configuration.

```groovy
plugins {
  id 'org.springframework.boot' version 'springBootVersion'
  id 'io.spring.dependency-management' version '1.1.0'
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
  implementation 'com.slack.api:bolt-jakarta-servlet:sdkLatestVersion'
}
```

If you have a certain reason to continue using Spring Boot 2 series, the build settings can be as below instead:

```groovy
plugins {
  id 'org.springframework.boot' version '2.7.5'
  id 'io.spring.dependency-management' version '1.1.0'
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
  implementation 'com.squareup.okhttp3:okhttp:okhttpVersion'
  implementation 'com.slack.api:bolt-servlet:sdkLatestVersion'
}
```

### src/main/java/hello/SlackApp.java

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

### `src/main/java/hello/SlackAppController.java`

This is a kind of boilerplate code to add an endpoint. You can customize the path by modifying the argument of `@WebServlet` annotation.

```java
package hello;

import com.slack.api.bolt.App;

import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;
import jakarta.servlet.annotation.WebServlet;

// With Spring Boot 2, the imports can be a bit different
// import com.slack.api.bolt.servlet.SlackAppServlet;
// import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
  public SlackAppController(App app) {
    super(app);
  }
}
```

### `src/main/java/hello/Application.java`

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

If your app is going to have its own `ServletContextListener`, placing the above Servlet class in a dedicated package plus passing the package name to scan as the `@ServletComponentScan`'s argument would be recommended. Refer to [#947](https://github.com/slackapi/java-slack-sdk/issues/947) for more details.

### `src/main/resources/application.yml`

Lastly, place a configuration file in the `resources` directory. The following example customizes the log level only for **Slack SDK for Java** and the port to listen from 8080 to 3000.

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
 :: Spring Boot ::        (vspringBootVersion)

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

If you prefer [Micronaut](https://micronaut.io/) rather than commonplace Servlet environments, add `bolt-micronaut`, NOT `bolt-jetty`. As the `bolt` dependency will be automatically resolved as the `bolt-micronaut`'s dependency, you don't need to add it. That's the same for Gradle projects.

```xml
<!-- Compatible with Micronaut compatibleMicronautVersion -->
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-micronaut</artifactId>
  <version>sdkLatestVersion</version>
</dependency>
```

### `src/main/java/hello/Application.java`

`Application.java` is a kind of boilerplate. You can cut and paste the following code as-is.

```java
package hello;

import io.micronaut.runtime.Micronaut;

public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
```

### `src/main/java/hello/AppFactory.java`

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

### `src/main/resources/application.yml`

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
## Quarkus Undertow

[Quarkus](https://quarkus.io/) is a Web application framework that supports packaging for GraalVM and HotSpot. In this section, I'll explain how to configure SlackAppServlet with the framework.

You can generate a blank project from [code.quarkus.io](https://code.quarkus.io/). For simple Bolt apps, we recommend using **Undertow Servlet** in the **Web** component section. Nothing else is required. Just click **Generate your application** button and download the generated zip file.

Also, if you don't have additional endpoints using RESTEasy, you can safely remove `quarkus-resteasy` (a dependency included by default).

### Build Settings

The following build setting files work as-is. Place either `pom.xml` (for Maven) or `build.gradle` + `settings.gradle` (for Gradle) in the root directory of your project.

#### Maven - pom.xml

The following settings are compatible with both of JDK 8 and 11. If you prefer using Java 11 features, set `11` to `maven.compiler.source` and `maven.compiler.target`,

```xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
     xmlns="http://maven.apache.org/POM/4.0.0"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.acme</groupId>
  <artifactId>code-with-quarkus</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <properties>
    <compiler-plugin.version>3.8.1</compiler-plugin.version>
    <failsafe.useModulePath>false</failsafe.useModulePath>
    <maven.compiler.release>11</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
    <quarkus.platform.group-id>io.quarkus.platform</quarkus.platform.group-id>
    <quarkus.platform.version>quarkusVersion</quarkus.platform.version>
    <slack-sdk.version>sdkLatestVersion</slack-sdk.version>
  </properties>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>${quarkus.platform.group-id}</groupId>
        <artifactId>${quarkus.platform.artifact-id}</artifactId>
        <version>${quarkus.platform.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.slack.api</groupId>
      <artifactId>bolt</artifactId>
      <version>${slack-sdk.version}</version>
    </dependency>
    <dependency>
      <groupId>com.slack.api</groupId>
      <artifactId>bolt-servlet</artifactId>
      <version>${slack-sdk.version}</version>
    </dependency>

    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-arc</artifactId>
      <version>${quarkus.platform.version}</version>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-core</artifactId>
      <version>${quarkus.platform.version}</version>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-undertow</artifactId>
      <version>${quarkus.platform.version}</version>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>${quarkus.platform.group-id}</groupId>
        <artifactId>quarkus-maven-plugin</artifactId>
        <version>${quarkus.platform.version}</version>
        <extensions>true</extensions>
        <executions>
          <execution>
            <goals>
              <goal>build</goal>
              <goal>generate-code</goal>
              <goal>generate-code-tests</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>${compiler-plugin.version}</version>
        <configuration>
          <compilerArgs>
            <arg>-parameters</arg>
          </compilerArgs>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

#### Gradle - 1) build.gradle

For Gradle projects, the following two files are required at least.

```groovy
plugins {
  id 'java'
  id 'io.quarkus'
}
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.quarkus:quarkus-undertow:quarkusVersion'
  implementation 'com.slack.api:bolt-servlet:sdkLatestVersion'
}
group 'org.acme'
version '1.0.0-SNAPSHOT'
```

#### Gradle - 2) settings.gradle

```groovy
pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
  plugins {
    id 'io.quarkus' version "quarkusVersion"
  }
}
rootProject.name='code-with-quarkus'
```

### `src/main/java/hello/SlackApp.java`

The only thing you need to do is to create a `@WebServlet`-wired class. The Quarkus framework scans such classes and enables them for you.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackApp extends SlackAppServlet {
  private static final long serialVersionUID = 1L;
  public SlackApp() { super(initSlackApp()); }
  public SlackApp(App app) { super(app); }

  private static App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### `src/main/kotlin/hello/SlackApp.java`

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

#### `src/main/kotlin/app.kt`

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

### `src/main/resources/application.properties`

The default port Quarkus uses is 8080. You can change the port by having the following config. Enabling Bolt's debug logging would be greatly helpful for learning how it works and debugging some unintended behaviors.

```
quarkus.http.port=3000
quarkus.log.level=INFO
quarkus.log.category."com.slack.api".level=DEBUG
quarkus.package.type=uber-jar
```

### Run the App

That’s all set! It’s time to run the app in development mode.

```bash
./mvnw quarkus:dev
./mvnw clean quarkus:dev # try clean when you don't see some updates
```

```bash
./gradlew quarkusDev
```

If your Quarkus project is correctly configured, the stdout should look like this.

```
[INFO] --- quarkus-maven-plugin:quarkusVersion:dev (default-cli) @ code-with-quarkus ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /path-to-projet/target/classes
Listening for transport dt_socket at address: 5005
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (main) code-with-quarkus 1.0.0-SNAPSHOT (powered by Quarkus quarkusVersion)) started in 0.846s. Listening on: http://0.0.0.0:3000
INFO  [io.quarkus] (main) Profile dev activated. Live Coding activated.
INFO  [io.quarkus] (main) Installed features: [cdi, servlet]
```

The hot reload mode is enabled by default.

```
INFO  [io.qua.dev] (vert.x-worker-thread-0) Changed source files detected, recompiling [/path-to-project/src/main/java/hello/SlackApp.java]
INFO  [io.quarkus] (vert.x-worker-thread-0) Quarkus stopped in 0.001s
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (vert.x-worker-thread-0) code-with-quarkus 1.0.0-SNAPSHOT (powered by Quarkus quarkusVersion) started in 0.021s. Listening on: http://0.0.0.0:3000
INFO  [io.quarkus] (vert.x-worker-thread-0) Profile dev activated. Live Coding activated.
INFO  [io.quarkus] (vert.x-worker-thread-0) Installed features: [cdi, servlet]
INFO  [io.qua.dev] (vert.x-worker-thread-0) Hot replace total time: 0.232s 
```

To build a runnable jar file for production deployment, you can run either `./mvnw package` or `./gradlew package` (or `./gradlew build -Dquarkus.package.type=uber-jar`).

---
## Helidon SE

[Helidon SE](https://helidon.io/docs/latest/#/about/02_introduction) is the functional programming style web framework provided by all Helidon libraries. Let's start with a blank project.

```bash
mvn archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeGroupId=io.helidon.archetypes \
  -DarchetypeArtifactId=helidon-quickstart-se \
  -DarchetypeVersion=helidonVersion \
  -DgroupId=com.exmple \
  -DartifactId=helidon-se-bolt-app \
  -Dpackage=hello
```

### pom.xml

The only thing you need to do with the build settings is adding `bolt-helidon` dependency and your favorite [SLF4J](http://www.slf4j.org/) implementation.

```xml
<dependency>
  <groupId>io.helidon.bundles</groupId>
  <artifactId>helidon-bundles-webserver</artifactId>
</dependency>
<dependency>
  <groupId>io.helidon.config</groupId>
  <artifactId>helidon-config-yaml</artifactId>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-helidon</artifactId>
  <version>sdkLatestVersion</version>
</dependency>
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.3</version>
</dependency>
```

### `src/main/java/hello/Main.java`

`bolt-helidon` is as handy as `bolt-jetty`. All developers need to do is define a main method that initializes **App**s and starts an HTTP server.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.helidon.SlackAppServer;
import com.slack.api.model.event.AppMentionEvent;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.metrics.MetricsSupport;

public final class Main {
  public static void main(final String[] args) { startServer(); }

  public static SlackAppServer startServer() {
    SlackAppServer server = new SlackAppServer(apiApp(), oauthApp());
    // If you add more settings to Routing, overwrite this configurator
    server.setAdditionalRoutingConfigurator(builder -> builder
      .register(MetricsSupport.create())
      .register(HealthSupport.builder().addLiveness(HealthChecks.healthChecks()).build()));
    server.start();
    return server;
  }

  // POST /slack/events - this path is configurable with bolt.apiPath in application.yaml
  public static App apiApp() {
    App app = new App();
    app.event(AppMentionEvent.class, (event, ctx) -> {
      ctx.say("May I help you?");
      return ctx.ack();
    });
    return app;
  }
}
```

### `src/main/resources/application.yml`

Use `application.yml` to configure your Helidon SE apps.

```yaml
server:
  port: 3000
  host: 0.0.0.0
bolt:
  apiPath: /slack/events
```

### `src/main/resources/logback.xml`

If you use logback library as the SLF4J logger implementation, a simple `logback.xml` would be like blow.

```xml
<configuration>
  <appender name="default" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%date %level [%thread] %logger{64} %msg%n</pattern>
    </encoder>
  </appender>
  <logger name="com.slack.api" level="debug"/>
  <logger name="io.helidon" level="debug"/>
  <root level="info">
    <appender-ref ref="default"/>
  </root>
</configuration>
```

### Run the App

The recommended way to start your app is either to use the [Helidon CLI](https://helidon.io/docs/latest/#/about/05_cli)'s dev mode 

```bash
helidon dev
```

or to build and run your app every time you've applied changes to it.

```bash
mvn exec:java -Dexec.mainClass="hello.Main"
# or
mvn package && java -jar target/helidon-se-bolt-app.jar
```

If the project is correctly configured, the stdout should look like this.

```bash
[main] io.helidon.webserver.NettyWebServer Version: helidonVersion
[nioEventLoopGroup-2-1] io.helidon.webserver.NettyWebServer Channel '@default' started: [id: 0x9fcf416d, L:/0:0:0:0:0:0:0:0:3000]
[nioEventLoopGroup-2-1] com.slack.api.bolt.helidon.SlackAppServer ⚡️ Bolt app is running!
```
