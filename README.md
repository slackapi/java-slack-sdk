## jSlack

[![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/jslack.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22) [![Build Status](https://travis-ci.org/seratch/jslack.svg?branch=master)](https://travis-ci.org/seratch/jslack)

jSlack is a Java library to easily integrate your operations with [Slack](https://slack.com/). The library currently supports the following APIs.

- [Incoming Webhooks](https://api.slack.com/incoming-webhooks)
- [API Methods](https://api.slack.com/methods)
- [Real Time Messaging API](https://api.slack.com/rtm)
- [SCIM API](https://api.slack.com/scim)
- [Audit Logs API](https://api.slack.com/docs/audit-logs-api)
- [Status API](https://api.slack.com/docs/slack-status)
- [Useful modules to build Slack App backend services](https://github.com/seratch/jslack/tree/master/jslack-app-backend/src/main/java/com/github/seratch/jslack/app_backend)
  - [OAuth 2.0](https://api.slack.com/docs/oauth)
  - [OAuth 2.0 - Granular Permissions](https://api.slack.com/authentication/basics)
  - [Events API](https://api.slack.com/events-api)
  - [Slash Commands](https://api.slack.com/slash-commands)
  - [Interactive Components](https://api.slack.com/messaging/interactivity)
  - [Message Actions](https://api.slack.com/actions)
  - [Modals](https://api.slack.com/surfaces/modals)
  - [App Home Tabs](https://api.slack.com/surfaces/tabs)
  - [Dialogs](https://api.slack.com/dialogs)
  - [Outgoing Webhooks](https://api.slack.com/custom-integrations/outgoing-webhooks)

### Getting Started

Check the latest version on [the Maven Central repository](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.github.seratch%22%20AND%20a%3A%22jslack%22).

#### API Client

The following is an example using Maven. Of course, it's also possible to grab the library via Gradle, sbt and any other build tools.

If you really don't need classes for building Slack app backend (= you need only Webhook/Web API/RTM API clients), just depending on `jslack-api-client` would be enough.

```xml
<groupId>com.github.seratch</groupId>
<artifactId>jslack-api-client</artifactId>
<version>{latest version}</version>
```

The `{latest version}` is [![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/jslack.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22)

See also: [Getting started with groovysh](https://github.com/seratch/jslack/wiki/Getting-Started-with-groovysh)

#### Slack App Server-side Framework

Lightning is a framework to build Slack apps on the JVM.

Yes, as you noticed, Lightning is highly inspired by [Bolt⚡](https://github.com/slackapi/bolt). The framework offers an abstraction layer on top of the web service infrastructure (e.g., Servlet API). The abstraction enables developers to focus on the essential parts of Slack app development.

Lighning is available on the Maven Central repository.

https://search.maven.org/search?q=g:com.github.seratch%20AND%20a:jslack-lightning

##### Java / Maven

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

### API Client Examples

If you like using Kotlin, check [this repository](https://github.com/seratch/jslack-kotlin-examples) as well 🙌

#### Incoming Webhooks

Incoming Webhooks is a simple way to post messages from external sources into Slack via ordinary HTTP requests.

https://api.slack.com/incoming-webhooks

jSlack naturally wraps its interface in Java. After lightly reading the official guide, you should be able to use it immediately.

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.webhook.*;

// https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
String url = System.getenv("SLACK_WEBHOOK_URL");

Payload payload = Payload.builder().text("Hello World!").build();

Slack slack = Slack.getInstance();
WebhookResponse response = slack.send(url, payload);
// response.code, response.message, response.body
```

Here is a small example using [Block Kit](https://api.slack.com/block-kit).

```java
import static com.github.seratch.jslack.api.webhook.WebhookPayloads.*;
import static com.github.seratch.jslack.api.model.block.Blocks.*;
import static com.github.seratch.jslack.api.model.block.element.BlockElements.*;
import static com.github.seratch.jslack.api.model.block.composition.BlockCompositions.*;

Payload payload = payload(b -> b.blocks(
  asBlocks(
    actions(a -> a.elements(asElements(
      button(bt -> bt.text(plainText(pt -> pt.emoji(true).text("label"))).value("value"))
    )))
  )
));

Slack slack = Slack.getInstance();
WebhookResponse response = slack.send(url, payload);
// response.code, response.message, response.body
```

It's also possible to directly give a raw payload.

```java
String payload = "{\"text\": \"Hi there!\"}";
WebhookResponse response = slack.send(url, payload);
```

#### API Methods

There are lots of APIs to integrate external sources into Slack. They follow HTTP RPC-style methods.

- https://api.slack.com/methods
- https://api.slack.com/web#basics

When the API call has been completed successfully, its response contains `"ok": true` and other specific fields.

```json
{
    "ok": true
}
```

In some cases, it may contain `warning` field too.

```json
{
    "ok": true,
    "warning": "something_problematic"
}
```

When the API call failed or had some warnings, its response contains `"ok": false'` and also have the `error` field which holds a string error code.

```json
{
    "ok": false,
    "error": "something_bad"
}
```


jSlack simply wrap API interface. Find more examples in this library's test code.

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.methods.request.conversations.*;
import com.github.seratch.jslack.api.methods.response.conversations.*;

Slack slack = Slack.getInstance();

String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
ConversationsCreateRequest req = ConversationsCreateRequest.builder().name(channelName).isPrivate(false).build();
ConversationsCreateResponse resp = slack.methods(token).conversationsCreate(publicChannelCreation);
```

Or, using lambda function to build a request could be much simpler. You don't need to type the long class name!

```java
final String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
ChannelsCreateResponse response =
  slack.methods(token).conversationsCreate(req -> req.name(channelName).isPrivate(false));
```

#### API Methods Examples

You can find more examples here: https://github.com/seratch/jslack/tree/master/src/test/java/com/github/seratch/jslack

##### Post a message to a channel

```java
Slack slack = Slack.getInstance();
String token = "xoxb-************************************";

// find all channels in the team
ConversationsListResponse listResponse = 
  slack.methods(token).conversationsList(req -> req.excludeArchived(true).limit(10));

// find #general
Conversation general = listResponse.getChannels().stream()
  .filter(c -> c.getName().equals("general"))
  .findFirst().get();

// https://slack.com/api/chat.postMessage
ChatPostMessageResponse postResponse =
  slack.methods(token).chatPostMessage(req -> req.channel(general.getId()).text("Hello World!"));

// timestamp of the posted message
String messageTs = postResponse.getMessage().getTs();

ChatDeleteResponse deleteResponse =
  slack.methods(token).chatDelete(req -> req.channel(general.getId()).ts(messageTs));
```

##### Open a Block Kit modal

```java
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.api.methods.response.views.ViewsOpenResponse;
import com.github.seratch.jslack.api.model.view.View;
import static com.github.seratch.jslack.api.model.block.Blocks.*;
import static com.github.seratch.jslack.api.model.block.composition.BlockCompositions.*;
import static com.github.seratch.jslack.api.model.view.Views.*;

// export SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET
App app = new App();

View view = view(r -> { return r
  .title(viewTitle(t -> t.text("My App")))
  .submit(viewSubmit(s -> s.text("Submit")))
  .close(viewClose(c -> c.text("Close")))
  .blocks(asBlocks(
    section(sec -> sec.text(plainText(pt -> pt.text("Hi")))),
    image(img -> img.imageUrl("https://www.example.com/foo.png"))
  ));
});

app.command("/open-modal", (req, ctx) -> {
  ViewsOpenResponse apiRes = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(view) // or .viewAsString(stringValue)
  );
  if (apiRes.isOk()) return ctx.ack();
  else return ctx.ack(r -> r.text("Failed to open a modal (error: " + apiRes.getError() + ")"));
});
```

#### Events API

The Events API is a streamlined, easy way to build apps and bots that respond to activities in Slack.
All you need is a Slack app and a secure place for us to send your events.

https://api.slack.com/events-api

```java
AppUninstalledHandler appUninstalledEventHandler = new AppUninstalledHandler {
  @Override
  public void handle(AppUninstalledPayload event) {
    // do something here
  }
};

public class SampleServlet extends SlackEventsApiServlet {
  @Override
  protected void setupDispatcher(EventsDispatcher dispatcher) {
    dispatcher.register(appUninstalledEventHandler);
  }
}
```

With Lightning, event handlers can be simpler:

```java
App app = new App();

app.event(MessageEvent.class, (event, ctx) -> {
  if (isRequest(event.getEvent().getText())) {
    ChatPostMessage response = ctx.client().chatPostMessage(r -> r.channel(event.getEvent().getChannel()).text("I got it!"));
    if (!response.isOk()) {
        // error handling
    }
  }
  return ctx.ack();
});
app.event(MessageDeletedEvent.class, (event, ctx) -> {
  return ctx.ack();
});
```


#### Slack App Backend Examples

jSlack offers a full-stack Slack app framework named _Lightning⚡_. Check [its README](https://github.com/seratch/jslack/tree/master/jslack-lightning) to learn the basics first.

```kotlin
import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.command("/echo") { req, ctx ->
        val text = "You said ${req.payload.text} at <#${req.payload.channelId}|${req.payload.channelName}>"
        val res = ctx.respond { it.text(text) }
        logger.info("respond result - {}", res)
        ctx.ack()
    }

    // Amazon Elastic Container Service - the default health check endpoint
    // [ "CMD-SHELL", "curl -f http://localhost/ || exit 1" ]
    // https://docs.aws.amazon.com/AmazonECS/latest/APIReference/API_HealthCheck.html
    app.endpoint("/") { _, ctx ->
        ctx.ack()
    }

    // export SLACK_PORT=8080
    val envPort: String? = System.getenv()["SLACK_PORT"]
    val port: Int = if (envPort == null) 8080 else Integer.valueOf(envPort)
    val server = SlackAppServer(app, port)
    server.start()
}
```

Also, there are lots of working examples:

* [Docker examples](https://github.com/seratch/jslack/tree/master/jslack-lightning-docker-examples)
* [Examples in Kotlin](https://github.com/seratch/jslack/tree/master/jslack-lightning-kotlin-examples)
* [Examples using Spring Boot](https://github.com/seratch/jslack/tree/master/jslack-lightning-spring-boot-examples/src/main/java/example)
* [Examples using Micronaut](https://github.com/seratch/jslack/tree/master/jslack-lightning-micronaut/src/test/java)
* [Examples using Quarkus](https://github.com/seratch/jslack/tree/master/jslack-lightning-quarkus-examples)

#### Real Time Messaging API

Real Time Messaging API is a WebSocket-based API that allows you to receive events from Slack in real time and send messages as user.

https://api.slack.com/rtm

When you use this API through jSlack library, you need adding additional WebSocket libraries:

```xml
<dependency>
    <groupId>javax.websocket</groupId>
    <artifactId>javax.websocket-api</artifactId>
    <version>1.1</version>
</dependency>
<dependency>
    <groupId>org.glassfish.tyrus.bundles</groupId>
    <artifactId>tyrus-standalone-client</artifactId>
    <version>1.13</version>
</dependency>
```

The following example shows you a simple usage of RTM API. 

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.rtm.*;
import com.google.gson.*;

JsonParser jsonParser = new JsonParser();
String token = System.getenv("SLACK_BOT_API_TOKEN");

try (RTMClient rtm = new Slack().rtm(token)) {

  rtm.addMessageHandler((message) -> {
    JsonObject json = jsonParser.parse(message).getAsJsonObject();
    if (json.get("type") != null) {
      log.info("Handled type: {}", json.get("type").getAsString());
    }
  });

  RTMMessageHandler handler2 = (message) -> {
    log.info("Hello!");
  };

  rtm.addMessageHandler(handler2);

  // must connect within 30 seconds after issuing wss endpoint
  rtm.connect();

  rtm.sendMessage(Typing.builder()
    .id(System.currentTimeMillis())
    .channel(channelId)
    .build().toJSONString());

  rtm.sendMessage(Message.builder()
    .id(System.currentTimeMillis())
    .channel(channelId)
    .text("Hi!")
    .build().toJSONString());

  rtm.removeMessageHandler(handler2);

} // #close method does #disconnect
```

The `message`, argument of messageHandler, is a string value in JSON format. You need to deserialize it with your favorite JSON library.

jSlack already depends on [google-gson](https://github.com/google/gson) library. So you can use Gson as above example shows. If you prefer Jackson or other libraries, it's also possible.


#### SCIM API

* [SCIM (System for Cross-domain Identity Management)](http://www.simplecloud.info/)
* [RFC 7644: System for Cross-domain Identity Management: Protocol](http://www.rfc-editor.org/rfc/rfc7644.txt)
* [SCIM API Response](https://github.com/seratch/jslack/tree/master/jslack-api-client/src/main/java/com/github/seratch/jslack/api/scim/response)

```java
// users
User newUser = buildNewUser(); // omitted
UsersCreateResponse creationResp = slack.scim(token).createUser(req -> req.user(newUser));

// https://api.slack.com/scim#filter
String filter = "userName eq \"" + newUser.getUserName() + "\"";
UsersSearchResponse searchResp = slack.scim(token).searchUsers(req -> req.count(1).filter(filter));
assertThat(searchResp.getItemsPerPage(), is(1));
assertThat(searchResp.getResources().size(), is(1));

User partialUpdate = new User();
partialUpdate.setUserName(newUserName);
slack.scim(token).patchUser(req -> req.id(userId).user(partialUpdate));

slack.scim(token).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

// groups
GroupsSearchResponse pagination = slack.scim(token).searchGroups(req -> req.count(1));
```

#### Audit Logs API

* [Audit Logs API Official Document](https://api.slack.com/docs/audit-logs-api)
* [Audit Logs API Response](https://github.com/seratch/jslack/tree/master/jslack-api-client/src/main/java/com/github/seratch/jslack/api/audit/response)

```java
SchemasResponse response = slack.audit(token).getSchemas();
ActionsResponse response = slack.audit(token).getActions();
LogsResponse response = slack.audit(token).getLogs(req -> req.oldest(1521214343).action("user_login").limit(10));
```

#### (jSlack's Original) Shortcut APIs

```java
Slack slack = Slack.getInstance();
ApiToken token = ApiToken.of(System.getenv("SLACK_OAUTH_ACCESS_TOKEN"));

Shortcut shortcut = slack.shortcut(token);

List<Message> messages = shortcut.findRecentMessagesByName(ChannelName.of("general"));
ReactionsAddResponse addReaction = shortcut.addReaction(messages.get(0), ReactionName.of("smile"));

ChatPostMessageResponse response = shortcut.post(ChannelName.of("general"), "hello, hello!");

// Good old attachments
Attachment attachment = Attachment.builder().text("text").footer("footer").build();
List<Attachment> attachments = Arrays.asList(attachment);
ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("general"), "hello, hello!");

// Block Kit
SectionBlock section = SectionBlock.builder()
  .text(MarkdownTextObject.builder().text("Some rich text").build())
  .accessory(ImageElement.builder().imageUrl("https://example.com/foo.jpg").altText("This is an image").build())
  .build();

DividerBlock divider = new DividerBlock();

ChatPostMessageResponse response = shortcut.post(ChannelName.of("general"),
  Arrays.asList(section, divider));
```


### Preparations for running this library's unit tests.

### Creating a Slack app

https://api.slack.com/apps

### Setting up "OAuth & Permissions" for it

You need to select all permission scopes except for `identity.*`. After that, you also need to run "Reinstall App".

#### Setting up OAuth access tokens (both the normal one and the one as a bot)

<img src="https://user-images.githubusercontent.com/19658/34647360-38a7bc9e-f3c4-11e7-8c68-64c9be5b6fc3.png">

Set the OAuth access tokens as env variables.

```bash
export SLACK_TEST_OAUTH_ACCESS_TOKEN=xoxp-*******************************************************
export SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN=xoxb-************************************
```

### Creating at least one Slack user who has its email

Manually create a Slack user which has an email address for a unit test.

### Create an incoming webhook url

```bash
export SLACK_WEBHOOK_TEST_URL=https://hooks.slack.com/services/Txxxx/yyy/zzz
export SLACK_WEBHOOK_TEST_CHANNEL=C12345678 (or #random)
```

### Add youtube.com to App Unfurl Domains

* Features > Event Subscriptions > App Unfurl Domains
  * Add `youtube.com` to the domain list
  * Re-install your Slack app

### (Optional) Have a shared channel

```bash
export SLACK_TEST_SHARED_CHANNEL_ID=C12345678
```

### (Optional) Have a multi-channel guest and a single-channel guest

### (Optional) Have an access token to run tests with SCIM API and Audit Logs API

```bash
export SLACK_TEST_ADMIN_OAUTH_ACCESS_TOKEN=xoxp-xxxx
```

### Run the tests

```bash
mvn test
```

## Deployment

```bash
mvn deploy -P release-sign-artifacts -D maven.test.skip=true
```

## License

(The MIT License)

Copyright (c) Kazuhiro Sera
