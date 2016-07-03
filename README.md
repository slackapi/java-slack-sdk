## jSlack

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.seratch/jslack/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.seratch/jslack)

jSlack is a Java library to easily integrate your operations with [Slack](https://slack.com/). Currently, this library supports the following APIs.

- Incoming Webhook
- Real Time Messaging API
- Methods
  - api.test
  - auth.revoke
  - auth.test
  - bots.info
  - channels.archive
  - channels.create
  - channels.history
  - channels.info
  - channels.list

### Getting Started

Check the latest version on [the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.seratch%22%20a%3A%22jslack%22).

The following is a Maven example. Of course, it's also possible to grab the library via Gradle, sbt and any other build tools.

```xml
<groupId>com.github.seratch</groupId>
<artifactId>jslack</artifactId>
<version>{latest version}</version>
```

### Examples

#### Incoming Webhoook

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.webhook.*;

String url = System.getenv("SLACK_WEBHOOK_URL");
Payload payload = Payload.builder()
  .channel("#random")
  .username("jSlack Bot")
  .iconEmoji(":smile_cat:")
  .text("Hello World!")
  .build();

new Slack().send(url, payload);
```

#### Real Time Messaging

Additionally, WebSocket libraries are required:


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

#### Methods

```java
import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.methods.*;

Slack slack = new Slack();

String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
ChannelsCreateRequest channelCreation = ChannelsCreateRequest.builder().token(token).name(channelName).build();
ChannelsCreateResponse response = slack.methods().channelsCreate(channelCreation);
```

### License

(The MIT License)

Copyright (c) Kazuhiro Sera

