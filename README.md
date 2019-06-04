## jSlack

[![Maven Central](https://img.shields.io/maven-central/v/com.github.seratch/jslack.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22) [![Build Status](https://travis-ci.org/seratch/jslack.svg?branch=master)](https://travis-ci.org/seratch/jslack)

jSlack is a Java library to easily integrate your operations with [Slack](https://slack.com/). The library currently supports the following APIs.

- [Incoming Webhook](https://api.slack.com/incoming-webhooks)
- [API Methods](https://api.slack.com/methods)
- [Real Time Messaging API](https://api.slack.com/rtm)
- [Useful modules to build Slack App backend services](https://github.com/seratch/jslack/tree/master/src/main/java/com/github/seratch/jslack/app_backend)
  - [Events API](https://api.slack.com/events-api)
  - [Slash Commands](https://api.slack.com/slash-commands)
  - [Dialogs](https://api.slack.com/dialogs)
  - [Interactive Messages](https://api.slack.com/interactive-messages)
  - [Message Actions](https://api.slack.com/actions)
  - [OAuth 2.0](https://api.slack.com/docs/oauth)

As per API Methods, this library supports all the APIs listed in [github.com/slackapi/slack-api-specs](https://github.com/slackapi/slack-api-specs) as of May 2018.

### Getting Started

Check the latest version on [the Maven Central repository](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.github.seratch%22%20AND%20a%3A%22jslack%22).

The following is an example using Maven. Of course, it's also possible to grab the library via Gradle, sbt and any other build tools.

```xml
<groupId>com.github.seratch</groupId>
<artifactId>jslack</artifactId>
<version>{latest version}</version>
```

See also: [Getting started with groovysh](https://github.com/seratch/jslack/wiki/Getting-Started-with-groovysh)

### Examples

#### Incoming Webhook

Incoming Webhook is a simple way to post messages from external sources into Slack via ordinary HTTP requests.

https://api.slack.com/incoming-webhooks

jSlack naturally wraps its interface in Java. After lightly reading the official guide, you should be able to use it immediately.

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.webhook.*;

// https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
String url = System.getenv("SLACK_WEBHOOK_URL");

Payload payload = Payload.builder()
  .channel("#random")
  .username("jSlack Bot")
  .iconEmoji(":smile_cat:")
  .text("Hello World!")
  .build();

Slack slack = Slack.getInstance();
WebhookResponse response = slack.send(url, payload);
// response.code, response.message, response.body
```

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
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.response.channels.*;

Slack slack = Slack.getInstance();

String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
ChannelsCreateRequest channelCreation = ChannelsCreateRequest.builder().token(token).name(channelName).build();
ChannelsCreateResponse response = slack.methods().channelsCreate(channelCreation);
```

Or, using lambda function to build a request could be much simpler. You don't need to type the long class name!

```java
final String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
ChannelsCreateResponse response =
  slack.methods().channelsCreate(req -> req.token(token).name(channelName).build());
```
#### API Methods Examples

You can find more examples here: https://github.com/seratch/jslack/tree/master/src/test/java/com/github/seratch/jslack

##### Post a message to a channel

```java
String token = "api-token";
Slack slack = Slack.getInstance();

// find all channels in the team
ChannelsListResponse channelsResponse = slack.methods().channelsList(req -> req.token(token).build());
assertThat(channelsResponse.isOk(), is(true));
// find #general
Channel general = channelsResponse.getChannels()
  .stream()
  .filter(c -> c.getName().equals("general"))
  .findFirst()
  .get();

// https://slack.com/api/chat.postMessage
ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(req -> req
  .token(token)
  .channel(general.getId())
  .text("Hello World!")
  .build());
assertThat(postResponse.isOk(), is(true));

// timestamp of the posted message
String messageTimestamp = postResponse.getMessage().getTs();

Thread.sleep(1000L);

ChatDeleteResponse deleteResponse = slack.methods().chatDelete(req -> req
  .token(token)
  .channel(general.getId())
  .ts(messageTimestamp)
  .build());
assertThat(deleteResponse.isOk(), is(true));
```

##### Open a dialog modal

```java
final String token = "api-token";
    
// Required.  See https://api.slack.com/dialogs#implementation
final String triggerId = "trigger-id";

Slack slack = Slack.getInstance();

DialogTextElement quanityTextElement = DialogTextElement.builder()
  .subtype(SubType.NUMBER)
  .label("Quantity")
  .name("quantity")
  .hint("The number you need")
  .maxLength(3)
  .minLength(1)
  .placeholder("Required quantity")
  .value("1")
  .build();

DialogSelectElement colourSelectElement = DialogSelectElement.builder()
  .name("colour")
  .label("Colour")
  .placeholder("Choose your preferred colour")
  .options(Arrays.asList(
    Option.builder().label("Red").value("#FF0000").build(),
    Option.builder().label("Green").value("#00FF00").build(),
    Option.builder().label("Blue").value("#0000FF").build(),
    Option.builder().label("Black").value("#000000").build(),
    Option.builder().label("White").value("#FFFFFF").build()
  ))
  .build();

Dialog dialog = Dialog.builder()
  .title("Request pens")
  .callbackId("pens-1122")
  .elements(Arrays.asList(quanityTextElement, colourSelectElement))
  .submitLabel("")
  .build();

DialogOpenResponse openDialogResponse = slack.methods().dialogOpen(req -> req
  .token(token)
  .triggerId(triggerId)
  .dialog(dialog)
  .build());
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

