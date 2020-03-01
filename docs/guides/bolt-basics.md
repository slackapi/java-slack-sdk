---
layout: default
title: "Bolt Basics"
lang: en
---

# Bolt Basics

**Bolt for Java** is a framework on the JVM that offers an abstraction layer to build Slack apps quickly using modern platform features.

This guide covers all the basics of Bolt app development. If you're not yet familiar with Slack app development in general, we recommend reading [An introduction to Slack apps](https://api.slack.com/start/overview).

## Start with the App class

The **App** class is a place to write only essential parts of your Slack app without being bothered by trifles.

The code configuring an **App** instance mainly consists of the ways to respond to incoming events from Slack such as user actions, command invocations, requests to load options in select menus, and any events you subscribe to in the Events API.

```java
import com.slack.api.bolt.App;

App app = new App();
app.command("/echo", (req, ctx) -> {
  return ctx.ack(req.getText());
});
```

## Dispatching Events

Here is the list of the available methods to dispatch events.

|Method|Constraints (value: type)|Description|
|-|-|-|
|**app.event**|event type: **Class\<Event\>**|[**Events API**]({{ site.url | append: site.baseurl }}/guides/events-api): Responds to any kinds of bot/user events you subscribe.|
|**app.command**|command name: **String** \| **Pattern**|[**Slash Commands**]({{ site.url | append: site.baseurl }}/guides/slash-commands): Responds to slash command invocations in the workspace.|
|**app.messageAction**|callback_id: **String** \| **Pattern**|[**Actions**]({{ site.url | append: site.baseurl }}/guides/actions): Responds to user actions in message menus.|
|**app.blockAction**|action_id: **String** \| **Pattern**|[**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components): Responds to user actions (e.g., click a button, choose an item from select menus, radio buttons, etc.) in **blocks**. These events can be triggered in all the surfaces (messages, modals, and Home tabs).|
|**app.blockSuggestion**|action_id: **String** \| **Pattern**|[**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components): Responds to user actions to input a keyword (the length needs to be the `min_query_length` or longer) in select menus (external data source).|
|**app.viewSubmission**|callback_id: **String** \| **Pattern**|[**Modals**]({{ site.url | append: site.baseurl }}/guides/modals): Responds to data submissions in modals.|
|**app.viewClosed**|callback_id: **String** \| **Pattern**|[**Modals**]({{ site.url | append: site.baseurl }}/guides/modals): Responds to the events where users close modals by clicking Cancel buttons. The `notify_on_close` has to be `true` when opening/pushing the modal.|
|**app.dialogSubmission**|callback_id: **String** \| **Pattern**|**Dialogs**: Responds to data submissions in dialogs.|
|**app.dialogSuggestion**|callback_id: **String** \| **Pattern**|**Dialogs**: Responds to requests to load options for external typed select menus in dialogs.|
|**app.dialogCancellation**|callback_id **String** \| **Pattern**|**Dialogs**: Responds to the events where users close dialogs by clicking Cancel buttons.|
|**app.attachmentAction**|callback_id: **String** \| **Pattern**|**Legacy Messaging**: Responds to user actions in **attachements**. These events can be triggered in only messages.|

## Development Guides by Feature

On these guide pages, you'll find a more concrete example code for each.

* [**Slash Commands**]({{ site.url | append: site.baseurl }}/guides/slash-commands)
* [**Actions**]({{ site.url | append: site.baseurl }}/guides/actions)
* [**Interactive Components**]({{ site.url | append: site.baseurl }}/guides/interactive-components)
* [**Modals**]({{ site.url | append: site.baseurl }}/guides/modals)
* [**App Home**]({{ site.url | append: site.baseurl }}/guides/app-home)
* [**Events API**]({{ site.url | append: site.baseurl }}/guides/events-api)
* [**App Distribution (OAuth Flow)**]({{ site.url | append: site.baseurl }}/guides/app-distribution)

## Acknowledge Incoming Requests

Actions, commands, and options events must always be acknowledged using the `ack()` method. All such utility methods are available as the instance methods of a **Context** object.

```java
app.command("/hello", (req, ctx) -> { // ctx: Context
  return ctx.ack() // empty body, that means your bot won't post a reply this time
});
```

If your app replies to a user action, you can pass a message text to the `ack()` method.

```java
app.command("/ping", (req, ctx) -> {
  return ctx.ack(":wave: pong");
});
```

It's also possible to use [Block Kit](https://api.slack.com/block-kit) to make messages more interactive.

```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

app.command("/ping", (req, ctx) -> {
  return ctx.ack(asBlocks(
    section(section -> section.text(markdownText(":wave: pong"))),
    actions(actions -> actions
      .elements(asElements(
        button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
      ))
    )
  ));
});
```

By default, the reply will be sent as an ephemeral message. To send a message visible to everyone, use `in_channel` type.

```java
app.command("/ping", (req, ctx) -> {
  return ctx.ack(res -> res.responseType("in_channel").text(":wave: pong"));
});
```

## Respond to User Actions

Are you already familiar with `response_url`? If not, we recommend reading [this guide](https://api.slack.com/interactivity/handling#message_responses) first.

As the guide says, some of the user interaction payloads may contain a `response_url`. This `response_url` is unique to each payload, and can be used to publish messages back to the place where the interaction happened.

Similarly to `ack()`above, the **Context** object offers `respond()` method for easily taking advantage of `response_url`.

```java
import com.slack.api.webhook.WebhookResponse;

app.command("/hello", (req, ctx) -> {
  // Post a message via response_url
  WebhookResponse result = ctx.respond(res -> res
    .responseType("ephemeral") // or "in_channnel"
    .text("Hi there!") // blocks, attachments are also available
  );
  return ctx.ack(); // ack() here doesn't post a message
});
```

## Use Web APIs

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

For [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) API calls, using `say()` utility is much simpler.

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
    return ctx.ack("Please give some query.");
  }

  String userToken = ctx.getRequestUserToken(); // enabling InstallationService required
  if (userToken != null) {
    SearchMessagesResponse response = ctx.client().searchMessages(r -> r
      .token(userToken) // Overwrite underlying bot token with the given user token
      .query(query));
    if (response.isOk()) {
      String reply = response.getMessages().getTotal() + " results found for " + query;
      return ctx.ack(reply);
    } else {
      String reply = "Failed to search by " + query + " (error: " + response.getError() + ")";
      return ctx.ack(reply);
    }
  } else {
    return ctx.ack("Please allow this Slack app to run search queries for you.");
  }
});
```

## Use Logger

You can access [SLF4J](http://www.slf4j.org/) logger in **Context** objects.

```java
app.command("/weather", (req, ctx) -> {
  String keyword = req.getPayload().getText();
  String userId = req.getPayload().getUserId();
  ctx.logger.info("Weather search by keyword: {} for user: {}", keyword, userId);
  return ctx.ack(weatherService.find(keyword).toMessage());
});
```

If you use the [**ch.qos.logback:logback-classic**](https://search.maven.org/artifact/ch.qos.logback/logback-classic/1.2.3/jar) library as the implementation of the APIs, you can configure the settings by [**logback.xml**](http://logback.qos.ch/manual/configuration.html) etc.

```xml
<configuration>
  <appender name="default" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%date %level [%thread] %logger{64} %msg%n</pattern>
    </encoder>
    </appender>
  <root level="debug">
    <appender-ref ref="default"/>
  </root>
</configuration>
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
      resp.getHeaders().put("content-type", resp.getContentType());
      // dump all the headers as a single string
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

## Supported Web Frameworks

Refer to [this page]({{ site.url | append: site.baseurl }}/guides/supported-web-frameworks) for more details.

## Deployments

[We're planning](https://github.com/slackapi/java-slack-sdk/issues/348) to have some guide documents for deployments.
