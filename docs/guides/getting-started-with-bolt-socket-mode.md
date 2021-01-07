---
layout: default
title: "Getting Started with Bolt (Socket Mode)"
lang: en
---

# Getting Started with Bolt (Socket Mode)

**Bolt for Java** is a framework on the JVM that offers an abstraction layer to build Slack apps quickly using modern platform features.

This guide explains how to start your first-ever Bolt app.

* Project Setup
  * [Maven](#maven)
  * [Gradle](#gradle)
* Run Your Bolt App in 3 Minutes
  * Use **bolt-socket-mode**
  * Start the App with Two Env Variables
  * Enable `/hello` Command
* Getting Started in Kotlin
  * Make Sure If It Works
* Next Steps

If you're not yet familiar with Slack app development in general, we recommend reading [An introduction to Slack apps](https://api.slack.com/start/overview).

---
## Project Setup

Let's start building a Slack app using Bolt! This guide includes instructions on how to set up a Bolt project with Maven and Gradle, so use whichever section you'd like.

### Maven

After you create your Maven project, you need to add the **bolt** dependency to your `pom.xml` file. The **bolt** dependency is a framework-agnostic module. To enable Socket Mode, the **bolt-socket-mode** library and its provided-scope dependencies are also required for your app.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-socket-mode</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
<dependency>
  <groupId>javax.websocket</groupId>
  <artifactId>javax.websocket-api</artifactId>
  <version>1.1</version>
</dependency>
<dependency>
  <groupId>org.glassfish.tyrus.bundles</groupId>
  <artifactId>tyrus-standalone-client</artifactId>
  <version>1.17</version>
</dependency>
```

If you use Java-WebSocket library, add the artifact instead of **tyrus-standalone-client**.

```xml
<dependency>
  <groupId>org.java-websocket</groupId>
  <artifactId>Java-WebSocket</artifactId>
  <version>1.5.1</version>
</dependency>
```

### Gradle

After you create your Gradle project, add the **bolt** dependencies to `build.gradle`.

```groovy
dependencies {
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
}
```

---
## Run Your Bolt App in 3 Minutes

### Use **bolt-socket-mode**

**bolt-socket-mode** is a handy way to start your Socket Mode app. It allows developers to build a Slack app backend service by writing only a main method initializes **App** and establishes a WebSocket connection to the Socket Mode servers.

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
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
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

Only single source code is required to run your first-ever Bolt app. All you need to do is define the main method that starts **SocketModeApp**.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // App expects an env variable: SLACK_BOT_TOKEN
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    // SocketModeApp expects an env variable: SLACK_APP_TOKEN
    new SocketModeApp(app).start();
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
new SocketModeApp(app).start();
```

### Start the App with Two Env Variables

The default constructor expects the following two env variables exist when starting the app.

|Env Variable|Description|
|-|-|
|**SLACK_BOT_TOKEN**|The valid bot token value starting with `xoxb-` in your development workspace. To issue a bot token, you need to install your Slack App that has a bot user to your development workspace. Visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Settings** > **Install App** on the left pane (Add [`app_mentions:read`](https://api.slack.com/scopes/app_mentions:read) bot scope if you see the message saying "Please add at least one feature or permission scope to install your app.").<br/><br/> If you run an app that is installable for multiple workspaces, no need to specify this. Consult [App Distribution (OAuth)]({{ site.url | append: site.baseurl }}/guides/app-distribution) for further information instead.|
|**SLACK_APP_TOKEN**|The valid app-level token value starting with `xapp-` for your Slack app. To issue an app-level token, Visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Settings** > **Basic Information** > **App-Level Tokens**, and then create a new one with `connections:write` scope.|

If you prefer configuring an **App** in a different way, write some code to initialize **AppConfig** on your own.

Anyway, set the two env variables and hit `gradle run` on your terminal. The command runs your main method. For more detailed logging, `gradle run -DslackLogLevel=debug` is also available.

```bash
# Visit https://api.slack.com/apps to know these
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_APP_TOKEN=xapp-...your-own-valid-one

# run the main function
gradle run
```

You will see the message saying "**⚡️ Bolt app is running!**" in stdout.

If you get stuck this setup, go through the following checklist:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` has **bolt-jetty** dependency and valid application plugin settings
* ✅ `src/main/java/hello/MyApp.java` with a class having its main method
* ✅ [Create a Slack App](https://api.slack.com/apps?new_app=1), add [`commands`](https://api.slack.com/scopes/commands) bot scope, add **an app-level token with `connections:write` scope**, and install the app to your development workspace
* ✅ Copy [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) and [**App-Level Token**](https://api.slack.com/docs/token-types#app) from [your Slack App admin pages](https://api.slack.com/apps) and set them to env variables

### Enable `/hello` Command

Your app is up now! However, the slash command `/hello` in the code is still unavailable. To enable it, follow the steps below:

* Visit [Slack App configuration pages](https://api.slack.com/apps)
* Choose your app 
* Go to **Settings** > **Socket Mode** on the left pane
  * Turn on **Enable Socket Mode**
* Go to **Features** > **Slash Commands** on the left pane
  * Click **Create New Command** button
  * Input the command information on the dialog:
    * **Command**: `/hello`
    * **Short Description**: whatever you like
  * Click **Save** Button

Now you can hit the `/hello` command in your development workspace. If your app is successfully running, the app should respond to the command by replying `👋 Hello!`.

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
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
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
import com.slack.api.bolt.socket_mode.SocketModeApp

fun main() {
  val app = App()

  // Write some code here

  SocketModeApp(app).start()
}
```

### Make Sure If It Works

OK, you should be done. Just in case, here is the checklist:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` with valid Kotlin language settings and **bolt-jetty** dependency
* ✅ `src/main/kotlin/MyApp.kt` with a main method
* ✅ [Create a Slack App](https://api.slack.com/apps?new_app=1), add [`commands`](https://api.slack.com/scopes/commands) bot scope, add **an app-level token with `connections:write` scope**, and install the app to your development workspace
* ✅ Copy [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) and [**App-Level Token**](https://api.slack.com/docs/token-types#app) from [your Slack App admin pages](https://api.slack.com/apps) and set them to env variables

If all are ✅, bootstrapping your first-ever Kotlin-flavored Bolt app will succeed.

```bash
# Visit https://api.slack.com/apps to know these
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_APP_TOKEN=xapp-...your-own-valid-one

# run the main function
gradle run
```

... Did you see the message saying "**⚡️ Bolt app is running!**" in stdout?

If yes, that's all settled! 🎉

From here, all you need to do is write code and restart the app. Enjoy Bolt app development in Kotlin! 👋

**Pro tip**: We strongly recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) here even if you don't prefer using IDEs. The IDE is the smoothest way to try Kotlin application development.

---
## Next Steps

Read the [Bolt Basics]({{ site.url | append: site.baseurl }}/guides/bolt-basics) for further information.
