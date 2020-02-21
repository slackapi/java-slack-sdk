---
layout: default
title: "Bolt for Java️"
lang: en
---

# The Basics of Bolt️

**Bolt** is a framework on the JVM that offers an abstraction layer to build Slack apps safely and quickly.

This guide covers all the basics of Bolt app development. If you're not yet familiar with Slack app development in general, we recommend reading [An introduction to Slack apps](https://api.slack.com/start/overview) first.

---

## Project Setup

Let's start building a Slack app with Bolt! This guide shows how to set up a Bolt project with Maven, and Gradle.

### Maven

The first thing to do is to add the **bolt** dependency to your `pom.xml` anyway. If you use Bolt along with [Spring Boot](https://spring.io/projects/spring-boot), [Quarkus](https://quarkus.io/), and any others on top of Servlet environment, only the **bolt** library is required for your app.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

If you run the Bolt app on the Jetty HTTP server without any frameworks, you can simply go with **bolt-jetty** module.

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-jetty</artifactId> <!-- will resolve "bolt" artifact as its dependency -->
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

### Gradle

I don't repeat the Maven part. Just add necessary dependencies in your `build.gradle`.

```groovy
dependencies {
  implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")
  implementation("com.slack.api:bolt-jetty:{{ site.sdkLatestVersion }}")
}
```

---

## Run Your Bolt App in 3 Minutes

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
  implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")
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
      return ctx.ack(r -> r.text(":wave: Hello!"));
    });

    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
```

### Start the App

The default constructor expects the following two env variables exist when starting the app.

|Env Variable|Description|
|-|-|
|**SLACK_BOT_TOKEN**|The valid bot token value starting with `xoxb-` in your development workspace. To issue a bot token, you need to install your Slack App that has a bot user to your development workspace. Visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Settings** > **Install App** on the left pain. <br/><br/> If you run an app that is installable for multiple workspaces, no need to specify this. Consult [App Distribution (OAuth)]({{ site.url | append: site.baseurl }}/guides/app-distribution) for further information instead.|
|**SLACK_SIGNING_SECRET**|The secret value shared only with the Slack Platform. This secret value is used for verifying incoming requests from Slack. Request verification is crucial for security as Slack apps have internet-facing endpoints. To know the value, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, go to **Settings** > **Basic Information** on the left pane, and find **App Credentials** > **Signing Secret** on the page. Refer to [the document](https://api.slack.com/docs/verifying-requests-from-slack) for further information.|

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
* ✅ [Create a Slack App](https://api.slack.com/apps), add a bot user, install the app to your development workspace
* ✅ Copy [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) and [**Signing Secret**](https://api.slack.com/docs/verifying-requests-from-slack) from [your Slack App admin pages](https://api.slack.com/apps) and set them to env variables

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
    app.command("/hello", (req, ctx) -> ctx.ack(r -> r.text("Hi there!")));
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

## The Basics of App

The **App** class is a place to write only essential parts of your Slack app without being bothered by trifles.

The code mainly consists of the ways to respond to incoming events from Slack such as user actions, command invocations, requests to load options in select menus, and any events you subscribe to in the Events API.

If you have some experiences working with [Bolt](https://slack.dev/bolt/), the concept of **App** is nearly the same. 

### Development Guides by Feature

On these guide pages, you'll find a more concrete example code for each.

* [**Slash Commands**]({{ site.url | append: site.baseurl }}/guides/slash-commands)
* [**Actions**]({{ site.url | append: site.baseurl }}/guides/actions)
* [**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components)
* [**Modals**]({{ site.url | append: site.baseurl }}/guides/modals)
* [**App Home**]({{ site.url | append: site.baseurl }}/guides/app-home)
* [**Events API**]({{ site.url | append: site.baseurl }}/guides/events-api)
* [**App Distribution (OAuth Flow)**]({{ site.url | append: site.baseurl }}/guides/app-distribution)

### Dispatching Events

Here is the list of the available methods to dispatch events.

|Method|Constraints (value: type)|Description|
|-|-|-|
|**app.event**|event type: **Class\<Event\>**|[**Events API**]({{ site.url | append: site.baseurl }}/guides/events-api): Responds to any kinds of bot/workspace events you subscribe.|
|**app.command**|command name: **String** \| **Pattern**|[**Slash Commands**]({{ site.url | append: site.baseurl }}/guides/slash-commands): Responds to slash command invocations in the workspace.|
|**app.messageAction**|callback_id: **String** \| **Pattern**|[**Actions**]({{ site.url | append: site.baseurl }}/guides/actions): Responds to user actions in message menus.|
|**app.blockAction**|action_id: **String** \| **Pattern**|[**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components): Responds to user actions (e.g., click a button, choose an item from select menus, radio buttons, etc.) in **blocks**. These events can be triggered in all the surfaces (messages, modals, and Home tabs).|
|**app.blockSuggestion**|action_id: **String** \| **Pattern**|[**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components): Responds to user actions to input a keyword in select menus (external data source).|
|**app.viewSubmission**|callback_id: **String** \| **Pattern**|[**Modals**]({{ site.url | append: site.baseurl }}/guides/modals): Responds to data submissions in modals.|
|**app.viewClosed**|callback_id: **String** \| **Pattern**|[**Modals**]({{ site.url | append: site.baseurl }}/guides/modals): Responds to the events where users close modals by clicking Cancel buttons.|
|**app.dialogSubmission**|callback_id: **String** \| **Pattern**|**Dialogs**: Responds to data submissions in dialogs.|
|**app.dialogSuggestion**|callback_id: **String** \| **Pattern**|**Dialogs**: Responds to requests to load options for external typed select menus in dialogs.|
|**app.dialogCancellation**|callback_id **String** \| **Pattern**|**Dialogs**: Responds to the events where users close dialogs by clicking Cancel buttons.|
|**app.attachmentAction**|callback_id: **String** \| **Pattern**|**Legacy Messaging**: Responds to user actions in **attachements**. These events can be triggered in only messages.|

### Acknowledge Incoming Requests

Actions, commands, and options events must always be acknowledged using the `ack()` method. The difference from Bolt is, in Bolt, all such utility methods are available as the instance methods of **Context** objects.

```java
app.command("/hello", (req, ctx) -> { // ctx: Context
  return ctx.ack() // empty body, that means your bot won't post a reply this time
});
```

It's also possible to post a message as a reply to the user action.

```java
app.command("/ping", (req, ctx) -> {
  return ctx.ack(res -> res.text(":wave: pong"));
});
```

By default, the reply will be sent as an ephemeral message. To send a message visible to everyone, use `in_channel` type.

```java
app.command("/ping", (req, ctx) -> {
  return ctx.ack(res -> res
    .responseType("in_channel")
    .text(":wave: pong")
  );
});
```

### Respond to User Actions

Are you already familiar with `response_url`? If not, we recommend reading [this guide](https://api.slack.com/interactivity/handling#message_responses) first.

As the guide says, some of the user interaction payloads may contain a `response_url`. This `response_url` is unique to each payload, and can be used to publish messages back to the place where the interaction happened.

Similarly to **ack()** above, the **Context** object offers `respond()` method for easily taking advantage of `repsonse_url`.

```java
import com.slack.api.webhook.WebhookResponse;

app.command("/hello", (req, ctx) -> {
  // Post a message via response_url
  WebhookResponse result = ctx.respond(res -> res
    .responseType("ephemeral") // or "in_channnel"
    .text("Hi there!") // blocks, attachments are also available
  );
  return ctx.ack(); // ack() here doesn't post a mesage
});
```

### Use Web APIs

When you need to call some Slack Web APIs in Bolt apps, use `ctx.client()` for it. The **MethodsClient** created by the method already holds a valid bot token. So, you don't need to give a token to it. Just calling a method with parameters as below works for you.

```java
app.command("/hello", (req, ctx) -> {
  // ctx.client() holds a valid bot token
  ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
    .channel("C1234567")
    .text(":wave: How are you?")
  );
  return ctx.ack();
});
```

For `chat.postMessage` API calls, using `say()` utility is much simpler.

```java
app.command("/hello", (req, ctx) -> {
  ChatPostMessageResponse response = ctx.say(":wave: How are you?");
  return ctx.ack();
});
```

In the case to use a user token over a bot token, overwriting the token by giving a user token as an argument works.

```java
import com.slack.api.methods.response.search.SearchMessagesResponse;

app.command("/my-search", (req, ctx) -> {
  String query = req.getPayload().getText();
  if (query == null || query.trim().size() == 0) {
    return ctx.ack(r -> r.text("Please give some query."));
  }

  String userToken = ctx.getRequestUserToken(); // enabling InstallationService required
  if (userToken != null) {
    SearchMessagesResponse response = ctx.client().searchMessages(r -> r
      .token(userToken) // Overwrite underlying bot token with the given user token
      .query(query));
    if (response.isOk()) {
      String reply = response.getMessages().getTotal() + " results found for " + query;
      return ctx.ack(r -> r.text(reply));
    } else {
      String reply = "Failed to search by " + query + " (error: " + response.getError() + ")";
      return ctx.ack(r -> r.text(reply));
    }
  } else {
    return ctx.ack(r -> r.text("Please allow this Slack app to run search queries for you."));
  }
});
```

## Middleware

Bolt offers chaining middleware supports. You can customize **App** behavior by weaving a kind of filter to all events.

Here is an example demonstrating how it works. The middleware changes your app's behavior in error patterns only when `SLACK_APP_DEBUG_MODE` env variable exists.


```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.JsonOps;
import static java.util.stream.Collectors.joining;

class DebugResponseBody {
  String responseType; // ephemeral, in_channel
  String text;
}
String debugMode = System.getenv("SLACK_APP_DEBUG_MODE");

App app = new App();

if (debugMode != null && debugMode.equals("1")) { // enable only when SLACK_APP_DEBUG_MODE=1
  app.use((req, _resp, chain) -> {
    Response resp = chain.next(req);
    if (resp.getStatusCode() != 200) {
      // dump all the headers as a single string
      resp.getHeaders().put("content-type", resp.getContentType());
      String headers = resp.getHeaders().entrySet().stream()
        .map(e -> e.getKey() +  ": " + e.getValue() + "\n").collect(joining());

      // set an ephemeral message with useful information
      DebugResponseBody body = new DebugResponseBody();
      body.responseType = "ephemeral";
      body.text =
        ":warning: *[DEBUG MODE] Something is technically wrong* :warning:\n" +
        "Below is a response the Slack app was going to send...\n" +
        "*Status Code*: " + resp.getStatusCode() + "\n" +
        "*Headers*: ```" + headers + "```" + "\n" +
        "*Body*: ```" + resp.getBody() + "```";
      resp.setBody(JsonOps.toJsonString(body));

      resp.setStatusCode(200);
    }
    return resp;
  });
}
```

The middleware transforms an unsuccessful response such as 404 Not Found to a 200 OK response with an ephemeral message that tells useful information for debugging.

<img src="{{ site.url | append: site.baseurl }}/assets/images/bolt-middleware.png" width="600" />


#### Order of Execution in Middleware List

A set of the built-in middleware precedes your custom middleware. So, if the app detects something in built-in ones and stops calling `chain.next(req)`, succeeding ones won't be executed.

The most common would be the case where a request has been denied by **RequestVerification** middleware. After the denial, any middleware won't be executed, so that the above middleware also doesn't work for the case.

---

## Getting Started in Kotlin

For code simplicity, [Kotlin](https://kotlinlang.org/) language would be a great option for writing Bolt apps. In this section, you'll learn how to set up a Kotlin project for Bolt apps.

#### build.gradle

Most of the build settings are necessary for enabling Kotlin language. Adding **bolt-jetty** dependency is the only one that is specific to bolt.

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "1.3.61"
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")
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
import org.slf4j.LoggerFactory

fun main() {
  val logger = LoggerFactory.getLogger("main")
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
* ✅ [Create a Slack App](https://api.slack.com/apps), add a bot user, install the app to your development workspace
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

## Supported Web Frameworks

Refer to [this page]({{ site.url | append: site.baseurl }}/guides/supported-web-frameworks) for more details.

## Deployments

[We're planning](https://github.com/slackapi/java-slack-sdk/issues/348) to have some guide documents for deployments.
