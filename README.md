## jSlack

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.seratch/jslack/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.seratch/jslack)

jSlack is a Java library to easily integrate your operations with [Slack](https://slack.com/). Currently, this library supports the following APIs.

- [Incoming Webhook](https://api.slack.com/incoming-webhooks)
- [Real Time Messaging API](https://api.slack.com/rtm)
- [API Methods](https://api.slack.com/methods)
  - api.test
  - auth.*
  - bots.*
  - channels.*
  - chat.*
  - dnd.*
  - emoji.*
  - files.*
  - groups.*
  - im.*
  - mpim.*
  - pins.*
  - reminders.*
  - rtm.*
  - search.*
  - stars.*
  - team.*
  - usergroups.*
  - users.*

### Getting Started

Check the latest version on [the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22).

The following is a Maven example. Of course, it's also possible to grab the library via Gradle, sbt and any other build tools.

```xml
<groupId>com.github.seratch</groupId>
<artifactId>jslack</artifactId>
<version>{latest version}</version>
```

See also: [Getting started with groovysh](https://github.com/seratch/jslack/wiki/Getting-Started-with-groovysh)

### Examples

#### Incoming Webhook

Incoming Webhook is a simple way to post messages from external sources into Slack via normal HTTP requests.

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

  rtm.removeMessageHandler(handler2);

} // #close method does #disconnect
```

The `message`, argument of messageHandler, is a string value (it's JSON data). You need to deserialize it with your favorite JSON library.

jSlack already depends on [google-gson](https://github.com/google/gson) library. So you can use Gson as above example shows. If you prefer Jackson or other libraries, it's also possible.

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

#### API Methods Examples

You can find more examples here: https://github.com/seratch/jslack/tree/master/src/test/java/com/github/seratch/jslack

##### Post a message to a channel

```java
String token = "api-token";
Slack slack = Slack.getInstance();

// find all channels in the team
ChannelsListResponse channelsResponse = slack.methods().channelsList(
  ChannelsListRequest.builder().token(token).build());
assertThat(channelsResponse.isOk(), is(true));
// find #general
Channel general = channelsResponse.getChannels().stream()
        .filter(c -> c.getName().equals("general")).findFirst().get();

// https://slack.com/api/chat.postMessage
ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
  ChatPostMessageRequest.builder()
    .token(token)
    .channel(general.getId())
    .text("Hello World!")
    .build());
assertThat(postResponse.isOk(), is(true));

// timestamp of the posted message
String messageTimestamp = postResponse.getMessage().getTs();

Thread.sleep(1000L);

ChatDeleteResponse deleteResponse = slack.methods().chatDelete(
  ChatDeleteRequest.builder()
    .token(token)
    .channel(general.getId())
    .ts(messageTimestamp)
    .build());
assertThat(deleteResponse.isOk(), is(true));
```


### License

(The MIT License)

Copyright (c) Kazuhiro Sera

