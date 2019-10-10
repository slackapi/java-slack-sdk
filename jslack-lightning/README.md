# Lightning ⚡ - The fastest way to build Slack apps on the JVM

Lightning is a framework to build Slack apps on the JVM.

Yes, as you noticed, Lightning is highly inspired by [Bolt⚡](https://github.com/slackapi/bolt). The framework offers an abstraction layer on top of the web service infrastructure (e.g., Servlet API). The abstraction enables developers to focus on the essential parts of Slack app development.

If you're already familiar with jSlack assets (jslack-api-client, jslack-app-backend, etc), you'll quickly start with Lightning framework. If not, please don't be afraid of it! jSlack is a thin wrapper of Slack APIs so that it is also pretty easy to learn how to use it.

## Getting Started

### Download the libraries

Lighning is available on the Maven Central repository.

https://search.maven.org/search?q=g:com.github.seratch%20AND%20a:jslack-lightning

#### Java / Maven

```xml
<dependencies>
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>jslack-lightning</artifactId>
    <version>{latest version}</version>
  </dependency>
  <!-- if you go with Jetty server -->
  <dependency>
    <groupId>com.github.seratch</groupId>
    <artifactId>jslack-lightning-jetty</artifactId>
    <version>{latest version}</version>
  </dependency>
</dependencies>
```

The `{latest version}` is [![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/jslack.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22)

#### Kotlin / Gradle

If you go with Kotlin, we highly recommend using Gradle to build your apps. Here is a simple example of `build.gradle.kts`.

```kotlin
plugins {
  id("org.jetbrains.kotlin.jvm") version "1.3.50"
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.github.seratch:jslack-lightning:{latest version}")
  implementation("com.github.seratch:jslack-lightning-jetty:{latest version}")
  implementation("org.slf4j:slf4j-simple:1.7.28")
}
```

The `{latest version}` is [![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/jslack.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22)

### Your first app built with Lightning

Let's start with Lightning, anyway! The thing you need to run Slack app on the JVM is only a few lines of code.

#### Java

```java
import com.github.seratch.jslack.lightning.App;

// export SLACK_BOT_TOKEN=xoxb-***
// export SLACK_SIGNING_SECRET=123abc***
App app = new App();

// Handles requests triggered by /echo
app.command("/echo", (req, ctx) -> {
  // Context#respond sends a message using payload.responseUrl
  ctx.respond(r -> r.text(req.getPayload().getText()));
  // Context#ack returns 200 OK response to the request
  return ctx.ack();
});

import com.github.seratch.jslack.lightning.jetty.SlackAppServer;

SlackAppServer server = new SlackAppServer(app);
server.start(); // http://localhost:3000
```

#### Kotlin

```kotlin
import com.github.seratch.jslack.lightning.App

// export SLACK_BOT_TOKEN=xoxb-***
// export SLACK_SIGNING_SECRET=123abc***
val app = App()

app.command("/echo") { req, ctx ->
  ctx.respond { it.text(req.payload.text) }
  ctx.ack()
}

import com.github.seratch.jslack.lightning.jetty.SlackAppServer

val server = SlackAppServer(app)
server.start()
```

In the later parts of this guide, most of the code examples will be written in Kotlin for simplicity's sake.

## Supported Features

* Verifying requests from Slack
* Single team authorization (use only one bot token to run an app)
* Multiple teams authentication (manage bot token / user tokens for multiple workspaces)
  * Token storage abstraction (currently supported: local file system, Amazon S3)
* Handling a variety of payloads
  * Events (`event_callback`, `url_verification`)
  * Actions (`block_actions`, `block_suggestion`, `message_action`, `interactive_message`, `message_action`)
  * Dialogs (`dialog_submission`, `dialog_suggestion`, `dialog_cancellation`)
  * Views (`view_submission`, `view_closed`)
  * Slash Commands
  * Outgoing Webhooks
* OAuth Flow
  * Slack App installation URL generation
  * Handling OAuth callback requests

### Verifying requests from Slack

Lightning offers a middleware `RequestVerification` verifying requests to your endpoints from Slack.
Developers don't need to implement [the logic to verify the request header](https://api.slack.com/docs/verifying-requests-from-slack) on their own and `RequestVerification` will be automatically enabled for all the event request handlers.

### Single team authorization

When you build a Slack app that runs on only a single workspace, giving the bot token you got when installing the app into a workspace would be a easiest way to start. There are two ways to configure apps as below.

```kotlin
import com.github.seratch.jslack.lightning.*

// 1) Use env variables
// export SLACK_BOT_TOKEN=xoxb-***
// export SLACK_SIGNING_SECRET=123abc***
val app = App()

// 2) Manually initialize AppConfig
val config = AppConfig.builder().singleTeamBotToken("xoxb-***").signingSecret("123abc***").build()
val app = App(config)
```

### Multiple teams authorization

If a Slack app is distributed to multiple workspaces, the app must appropriately support OAuth flow, which is invoked when installing the app into a workspace. The Slack app needs to securely manage user tokens (& bot token if the app has a bot user) corresponding to each workspace.

https://api.slack.com/docs/oauth

Lightning offers the following storage implementations at this moment. We're planning to have others in future releases.

* Local file system (default)
* Amazon S3

#### OAuth Flow Supported App Example

Here is a simple example supporting OAuth flow. 

```kotlin
// export SLACK_APP_CLIENT_ID=1234567890.123456789012
// export SLACK_APP_CLIENT_SECRET=abcabcabcabcabcabcabcabcabc
// export SLACK_SIGNING_SECRET=123abc***
val mainApp = App()

mainApp.command("/say-something") { req, ctx ->
  val p = req.payload
  val text = "<@${p.userId}> said ${p.text} at <#${p.channelId}|${p.channelName}>"
  ctx.respond { it.text(text).responseType("in_channel") }
  ctx.ack()
}

// (For Slack app installation URL)
// export SLACK_APP_CLIENT_ID=1234567890.123456789012
// export SLACK_APP_CLIENT_SECRET=abcabcabcabcabcabcabcabcabc
// export SLACK_APP_OAUTH_START_PATH=/start
// export SLACK_APP_SCOPE=bot,something-else
// export SLACK_APP_REDIRECT_URI=https://your-domain/slack/oauth/callback

// (For OAuth call back URL)
// export SLACK_APP_OAUTH_CALLBACK_PATH=/callback
// export SLACK_APP_OAUTH_CANCELLATION_URL=https://your-domain/static-page/cancellation
// export SLACK_APP_OAUTH_COMPLETION_URL=https://your-domain/static-page/completion

val oauthApp = App().asOAuthApp(true)

val server = SlackAppServer(mapOf(
  "/slack/events" to mainApp, // handles action/event requests from Slack
  "/slack/oauth" to oauthApp // handles the OAuth flow with end users
))
server.start() // http://localhost:3000
```

The OAuth flow (Slack app installation) starts with `https://your-domain/slack/oauth/start` and the app receives `code` at `https://your-domain/slack/oauth/callback`. If the installation successfully finishes, the end user is redirected to `https://your-domain/slack/oauth/completion`. If an error occurs or the end user canncels the process, the user is redirected to `https://your-domain/slack/oauth/cancellation`.

#### Amazon S3 Support

If an app uses Amazon S3 storage or any other storage layer implementations, the app can be configured as below:

```kotlin
val oauthApp = App().asOAuthApp(true)

// export AWS_REGION=us-east-1
// export AWS_ACCESS_KEY_ID=AAAA*************
// export AWS_SECRET_ACCESS_KEY=4o7***********************
val awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE"
oauthApp.service(AmazonS3InstallationService(awsS3BucketName))
oauthApp.service(AmazonS3OAuthStateService(awsS3BucketName))

server.start() // http://localhost:3000
```

### Commands

https://api.slack.com/slash-commands

`app.command(command-name, handler)` registers a function to handle command payloads sent when a user runs a slash command.

```kotlin
app.command("/echo") { req, ctx ->
  ctx.respond { it.text(req.payload.text) } // use response_url
  ctx.ack()
}
```

### Events API

https://api.slack.com/events-api

`app.event(event-payload-class, handler)` registers a function to manipulate event data.

Lightning automatically handles `url_verification` requests, which come from Slack when setting the endpoint URL in the admin screen (https://api.slack.com/apps/{app id}). Developers don't need to do anything about it.

```java
// in Java
app.event(MessageEvent.class, (event, ctx) -> {
  return ctx.ack();
});
```

```kotlin
// in Kotlin
app.event(MessageEvent::class.java) { event, ctx ->
  ctx.ack()
}
```

### Actions

https://api.slack.com/messaging/interactivity

#### `block_actions`

`app.blockAction(action-id, handler)` registers a function to handle user interactions that happened in a block element.

```kotlin
app.blockAction("element-action-id") { req, ctx ->
  ctx.ack()
}
```

#### `block_suggestion`

`app.blockSuggestion(action-id, handler)` registers a function to handle requests sent when a user enters a keyword in a select menu with an external data source (type: `external_select`).

```kotlin
val allOptions = listOf(
  Option(PlainTextObject("Schedule", true), "schedule"),
  Option(PlainTextObject("Budget", true), "budget"),
  Option(PlainTextObject("Assignment", true), "assignment")
)

app.blockSuggestion("select-block-action-id") { req, ctx ->
  val keyword = req.payload.value
  val filteredOptions = allOptions.filter { o -> (o.text as PlainTextObject).text.contains(keyword) }
  if (filteredOptions.isEmpty()) {
    ctx.ack { it.options(allOptions) }
  } else {
    ctx.ack { it.options(filteredOptions) }
  }
}
```

#### `message_action`

https://api.slack.com/actions

`app.messageAction(callback-id, handler)` registers a function to handle requests sent when a user uses custom message actions.

```kotlin
app.messageAction("message-action-callback-id") { req, ctx ->
  // do anything here
  ctx.ack()
}
```

### Dialogs

https://api.slack.com/dialogs

`app.dialog{Submission|Suggestion|Cancellation}(callback-id, handler)` registers a function to handle requests sent when a user interacts with dialogs.

```kotlin
val allOptions = listOf(
  Option("Product", "prd"),
  Option("Design", "des"),
  Option("Engineering", "eng"),
  Option("Sales", "sls"),
  Option("Marketing", "mrk")
)
val dialog = ResourceLoader.load("dialogs/dialog.json")

app.command("/dialog") { req, ctx ->
  val resp = ctx.client().dialogOpen {
    it.triggerId(req.payload.triggerId).dialogAsString(dialog)
  }
  if (resp.isOk) ctx.ack()
  else ctx.ackWithJson(resp)
}

app.dialogSubmission("dialog-callback-id") { req, ctx ->
  if (req.payload.submission["comment"]!!.length < 10) {
    val errors = listOf(Error("comment", "must be longer than 10 characters"))
    ctx.ack { it.errors(errors) }
  } else {
    ctx.respond { it.text("Thanks!!") }
    ctx.ack()
  }
}

app.dialogSuggestion("dialog-callback-id") { req, ctx ->
  val keyword = req.payload.value
  val options = allOptions.filter { it.label.contains(keyword) }
  ctx.ack { it.options(options) }
}

app.dialogCancellation("dialog-callback-id") { req, ctx ->
  ctx.respond { it.text("Next time :smile:") }
  ctx.ack()
}
```

### Views (Block Kit in modals)

https://api.slack.com/block-kit/surfaces/modals

`app.view{Submission|Closed}(callback-id, handler)` registers a function to handle requests sent when a user interacts with modals.

```kotlin
app.command("/meeting") { _, ctx ->
  val res = ctx.client().viewsOpen { it.triggerId(ctx.triggerId).viewAsString(view) }
  if (res.isOk) ctx.ack()
  else Response.builder().statusCode(500).body(res.error).build()
}

// when a user enters some word in "Topics"
app.blockSuggestion("topics-input") { req, ctx ->
  val keyword = req.payload.value
  val options = allOptions.filter { (it.text as PlainTextObject).text.contains(keyword) }
  ctx.ack { it.options(if (options.isEmpty()) allOptions else options) }
}
// when a user chooses an item from the "Topics"
app.blockAction("topics-input") { _, ctx -> ctx.ack() }

// when a user clicks "Submit"
app.viewSubmission("meeting-arrangement") { req, ctx ->
  val stateValues = req.payload.view.state.values
  val agenda = stateValues["agenda"]!!["agenda-input"]!!.value
  val errors = mutableMapOf<String, String>()
  if (agenda.length <= 10) {
    errors["agenda"] = "Agenda needs to be longer than 10 characters."
  }
  if (errors.isNotEmpty()) {
    ctx.ack { it.responseAction("errors").errors(errors) }
  } else {
    // Operate something with the data
    logger.info("state: $stateValues private_metadata: ${req.payload.view.privateMetadata}")
    ctx.ack()
  }
}

// when a user clicks "Cancel"
app.viewClosed("meeting-arrangement") { _, ctx ->
  ctx.ack()
}
```

### Create your own custom middleware

`Middleware` is a powerful tool to commonize some operations and run the code when receiving all the requests for sure.

With `Middlware`, developers can do anything like making any modifications to `Request` and `Response`, or skipping the latter operations when an expected request is detected.

```kotlin
app.use { req, _resp, chain ->
  logger.info("Request - $req")
  val resp = chain.next(req)
  logger.info("Response - $resp")
  resp
}
```

### More examples

If you're looking for more examples, you may find the ones under the following directories.

* Java - https://github.com/seratch/jslack/tree/master/jslack-lightning/src/test/java/samples
* Kotlin - https://github.com/seratch/jslack/tree/master/jslack-lightning-kotlin-examples/src/main/kotlin/examples
