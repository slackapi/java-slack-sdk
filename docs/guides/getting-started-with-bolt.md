---
layout: default
title: "Getting Started with Bolt"
lang: en
---

# Getting Started with Bolt

**Bolt for Java** is a framework on the JVM that offers an abstraction layer to build Slack apps quickly using modern platform features.

This guide explains how to start your first-ever Bolt app.

* Project Setup
  * Maven
  * Gradle
* Run Your Bolt App in 3 Minutes
  * Use **bolt-jetty**
  * Start the App with Two Env Variables
  * Enable `/hello` Command
  * OK, What about Spring Boot?
* Getting Started in Kotlin
  * Make Sure If It Works
* Next Steps

If you're not yet familiar with Slack app development in general, we recommend reading [An introduction to Slack apps](https://api.slack.com/start/overview).

---

## Project Setup

Let's start building a Slack app with Bolt! This guide shows how to set up a Bolt project with Maven, and Gradle.

### Maven

The first thing to do is to add the **bolt** dependency to your `pom.xml` anyway. The **bolt** dependency is a framework-agnostic module. If you use Bolt along with [Spring Boot](https://spring.io/projects/spring-boot), [Quarkus (Undertow)](https://quarkus.io/), and any others on top of Servlet environment, the **bolt-servlet** library is required for your app. Adding only **bolt-servlet** also works for you.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-servlet</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

If you run the Bolt app on the Jetty HTTP server without any frameworks, you can simply go with **bolt-jetty** module.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-jetty</artifactId> <!-- will resolve "bolt" and "bolt-servlet" artifacts as its dependencies -->
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

### Gradle

I don't repeat the Maven part. Just add necessary dependencies in your `build.gradle`.

```groovy
dependencies {
  implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")
  implementation("com.slack.api:bolt-servlet:{{ site.sdkLatestVersion }}")
  implementation("com.slack.api:bolt-jetty:{{ site.sdkLatestVersion }}")
}
```

---

## Run Your Bolt App in 3 Minutes

### Use **bolt-jetty**

**bolt-jetty** is a handy way to start your Slack app server. It allows developers to build a Slack app backend service by writing only a main method initializes **App** and starts an HTTP server.

#### build.gradle

The following build settings should be working as-is. Put it in the root directory of your project.

```groovy
plugins {
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation("com.slack.api:bolt-jetty:{{ site.sdkLatestVersion }}")
  implementation("org.slf4j:slf4j-simple:1.7.30")
}
application {
  mainClassName = "hello.MyApp"
}
run {
  // gradle run -DslackLogLevel=debug
  systemProperty "org.slf4j.simpleLogger.log.com.slack.api", System.getProperty("slackLogLevel")
}
```

#### src/main/java/hello/MyApp.java

Coding with this framework is much simpler than you think.

Only single source code is required to run your first-ever Bolt app. All you need to do is define the main method that starts **SlackAppServer**. Your server with the default configuration will listen to the 3000 port but it's configurable. Check other constructors of the class to customize the behavior.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
```

If you go with JDK 10+, thanks to [Local Variable Type Inference](https://developer.oracle.com/java/jdk-10-local-variable-type-inference.html), your code could be much more concise. To take advantage of it, install OpenJDK 11 and set the compatible Java versions in `build.gradle` as below. Also, configure the same on your IDE.

```groovy
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}
```

Now, you don't need to repeat the same type in a single line.

```java
var app = new App();
app.command("/hello", (req, ctx) -> {
  return ctx.ack(":wave: Hello!");
});
var server = new SlackAppServer(app);
server.start();
```

### Start the App with Two Env Variables

The default constructor expects the following two env variables exist when starting the app.

|Env Variable|Description|
|-|-|
|**SLACK_BOT_TOKEN**|The valid bot token value starting with `xoxb-` in your development workspace. To issue a bot token, you need to install your Slack App that has a bot user to your development workspace. Visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Settings** > **Install App** on the left pane. <br/><br/> If you run an app that is installable for multiple workspaces, no need to specify this. Consult [App Distribution (OAuth)]({{ site.url | append: site.baseurl }}/guides/app-distribution) for further information instead.|
|**SLACK_SIGNING_SECRET**|The secret value shared only with the Slack Platform. It is used for verifying incoming requests from Slack. Request verification is crucial for security as Slack apps have internet-facing endpoints. To know the value, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, go to **Settings** > **Basic Information** on the left pane, and find **App Credentials** > **Signing Secret** on the page. Refer to [the document](https://api.slack.com/docs/verifying-requests-from-slack) for further information.|

If you prefer configuring an **App** in a different way, write some code to initialize **AppConfig** on your own.

Anyway, set the two env variables and hit `gradle run` on your terminal. The command runs your main method. For more detailed logging, `gradle run -DslackLogLevel=debug` is also available.

```bash
# Visit https://api.slack.com/apps to know these
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_SIGNING_SECRET=123abc...your-own-valid-one

# run the main function
gradle run
```

You will see the message saying "**⚡️ Bolt app is running!**" in stdout.

If you get stuck this setup, go through the following checklist:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` has **bolt-jetty** dependency and valid application plugin settings
* ✅ `src/main/java/hello/MyApp.java` with a class having its main method
* ✅ [Create a Slack App](https://api.slack.com/apps?new_app=1), add [`app_mentions:read`](https://api.slack.com/scopes/app_mentions:read) bot scope, install the app to your development workspace
* ✅ Copy [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) and [**Signing Secret**](https://api.slack.com/docs/verifying-requests-from-slack) from [your Slack App admin pages](https://api.slack.com/apps) and set them to env variables

### Enable `/hello` Command

Your app is up now! However, the slash command `/hello` in the code is still unavailable. To enable it, follow the steps below:

* Set up some way to allow Slack API server to access your Bolt app
  * A well-known way is to use [ngrok](https://ngrok.com/) - install it and run `ngrok http 3000` on another terminal
* Configure & Reinstall the Slack App 
  * Visit [Slack App configuration pages](https://api.slack.com/apps)
  * Choose your app, go to **Features** > **Slash Commands** on the left pane
  * Click **Create New Command** button
  * Input the command information on the dialog:
    * **Command**: `/hello`
    * **Request URL**: `https://{your domain here}/slack/events` - if you use ngrok for development, the URL would be `https://{random}.ngrok.io/slack/events`
    * **Short Description**: whatever you like
  * Click **Save** Button
  * Go to **Settings** > **Install App** and click **Reinstall App** button

Now you can hit the `/hello` command in your development workspace. If your app is successfully running, the app should respond to the command by replying `👋 Hello!`.

### OK, What about Spring Boot?

As [Spring Boot](https://spring.io/projects/spring-boot) is one of the most popular web frameworks in the Java world, you may be curious about the possibility to let this Bolt live together with it.

Rest assured about it! It's quick and easy to _inject_ Bolt into Spring Boot apps.

All you need to do is add `implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")` to `dependencies` in `build.gradle` and write a few lines of code.

```java
@Configuration
public class SlackApp {
  @Bean
  public App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> ctx.ack("Hi there!"));
    return app;
  }
}

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
  public SlackAppController(App app) {
    super(app);
  }
}
```

Check [the detailed guide here]({{ site.url | append: site.baseurl }}/guides/supported-web-frameworks) for further information.

---

## Getting Started in Kotlin

For code simplicity, [Kotlin](https://kotlinlang.org/) language would be a great option for writing Bolt apps. In this section, you'll learn how to set up a Kotlin project for Bolt apps.

#### build.gradle

Most of the build settings are necessary for enabling Kotlin language. Adding **bolt-jetty** dependency is the only one that is specific to Bolt.

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "{{ site.kotlinVersion }}"
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:bolt-jetty:{{ site.sdkLatestVersion }}")
  implementation("org.slf4j:slf4j-simple:1.7.30") // or logback-classic
}
application {
  mainClassName = "MyAppKt" // add "Kt" suffix for main function source file
}
```

If you're already familiar with Kotlin and prefer the Gradle Kotlin DSL, of course, there is nothing stopping you.

#### src/main/kotlin/MyApp.kt

Here is a minimum source file that just starts a Bolt app on your local machine.

```kotlin
import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer

fun main() {
  val app = App()

  // Write some code here

  val server = SlackAppServer(app)
  server.start() // http://localhost:3000/slack/events
}
```

### Make Sure If It Works

OK, you should be done. Just in case, here is the checklist:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` with valid Kolitn language settings and **bolt-jetty** dependency
* ✅ `src/main/kotlin/MyApp.kt` with a main method
* ✅ [Create a Slack App](https://api.slack.com/apps?new_app=1), add [`app_mentions:read`](https://api.slack.com/scopes/app_mentions:read) bot scope, install the app to your development workspace
* ✅ Copy [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) and [**Signing Secret**](https://api.slack.com/docs/verifying-requests-from-slack) from [your Slack App admin pages](https://api.slack.com/apps) and set them to env variables

If all are ✅, bootstrapping your first-ever Kotlin-flavored Bolt app will succeed.

```bash
# Visit https://api.slack.com/apps to know these
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_SIGNING_SECRET=123abc...your-own-valid-one

# run the main function
gradle run
```

... Did you see the message saying "**⚡️ Bolt app is running!**" in stdout?

If yes, that's all settled! 🎉

From here, all you need to do is write code and restart the app. Enjoy Bolt app development in Kotlin! 👋

**Pro tip**: We strongly recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) here even if you don't prefer using IDEs. The IDE is the smoothest way to try Kotlin application development.

## Next Steps

Read the [Bolt Basics]({{ site.url | append: site.baseurl }}/guides/bolt-basics) for further information.

If you want to know ways to run Bolt app with Spring Boot, Micronaut, Quarkus, or Helidon SE, refer to [Supported Web Frameworks]({{ site.url | append: site.baseurl }}/guides/supported-web-frameworks).

Also, many examples are available in the GitHub repository.

* [Example apps with Spring Boot](https://github.com/slackapi/java-slack-sdk/tree/master/bolt-spring-boot-examples)
* [Example apps with Micronaut](https://github.com/slackapi/java-slack-sdk/tree/master/bolt-micronaut/src/test/java/example)
* [Example apps with Quarkus](https://github.com/slackapi/java-slack-sdk/tree/master/bolt-quarkus-examples)
* [Example apps in Kotlin](https://github.com/slackapi/java-slack-sdk/tree/master/bolt-kotlin-examples)
* [Example apps with Docker](https://github.com/slackapi/java-slack-sdk/tree/master/bolt-docker-examples)
