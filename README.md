## jSlack

jSlack is a Java library to easily integrate your operations with [Slack](https://slack.com/). Currently, this library supports the following APIs.

- Incoming Webhook
- Real Time Messaging API

### Getting Started

Check the latest version on [the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jslack%22) or [GitHub releases](https://github.com/seratch/jslack/releases).

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
import com.github.seratch.jslack.webhook.*;

String url = System.getenv("SLACK_WEBHOOK_URL");

Slack slack = new Slack();
WebhookPayload payload = new WebhookPayload();
payload.setChannel("#random");
payload.setUsername("jSlack Bot");
payload.setIconEmoji(":smile_cat:");
payload.setText("Hello World!");

slack.send(url, payload);
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
import com.github.seratch.jslack.rtm.*;
import com.google.gson.*;

String token = System.getenv("SLACK_BOT_API_TOKEN");
Slack slack = new Slack(token);

try (RTMClient rtm = slack.createRTMClient()) {
  RTMMessageHandler handler1 = (message) -> {
    JsonParser jsonParser = new JsonParser();
    JsonObject json = jsonParser.parse(message).getAsJsonObject();
    if (json.get("type") != null) {
      log.info("Handled type: {}", json.get("type").getAsString());
    }
  };
  RTMMessageHandler handler2 = (message) -> {
    log.info("Hello!");
  };
  rtm.addMessageHandler(handler1);
  rtm.addMessageHandler(handler2);
  rtm.connect();
  rtm.removeMessageHandler(handler2);
}
```


### License

(The MIT License)

Copyright (c) Kazuhiro Sera

