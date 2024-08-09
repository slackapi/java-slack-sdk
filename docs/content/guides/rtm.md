---
lang: en
---

# Real Time Messaging (RTM)

[The Real Time Messaging API](https://api.slack.com/rtm) is a WebSocket-based API that allows you to receive events from Slack in real-time and send messages as users. It's sometimes referred to just as the "RTM API".

:::danger 

RTM isn't available for modern scoped apps anymore. We recommend using the [Events API](/guides/events-api) and [Web API](/guides/web-api-basics) instead. If you need to use RTM (possibly due to corporate firewall limitations), you can do so by creating a legacy scoped app. If you have an existing RTM app, do not update its scopes as it will be updated to a modern scoped app and stop working with RTM.

:::

---
## Prerequisites

To use the RTM Client, in addition to the `slack-api-client` library, `javax.websocket-api` and `tyrus-standalone-client (1.x)` are required. Here is a minimum Maven settings file.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>awesome-slack-app</artifactId>
  <version>0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
  <dependencies>
    <dependency>
      <groupId>com.slack.api</groupId>
      <artifactId>slack-api-client</artifactId>
      <version>sdkLatestVersion</version>
    </dependency>
    <dependency>
      <groupId>javax.websocket</groupId>
      <artifactId>javax.websocket-api</artifactId>
      <version>javaxWebsocketApiVersion</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.tyrus.bundles</groupId>
      <artifactId>tyrus-standalone-client</artifactId>
      <version>tyrusStandaloneClientVersion</version>
    </dependency>
  </dependencies>
</project>
```

---
## Subscribing Slack Events Over WebSocket

Here is a minimum working example demonstrating how event handlers work.

```java
import com.slack.api.Slack;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.*;

// Dispatches incoming message events from RTM API
RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();

// Register a event handler runtime
RTMEventHandler<UserTypingEvent> userTyping = new RTMEventHandler<UserTypingEvent>() {
  @Override
  public void handle(UserTypingEvent event) {
    // do something here
  }
};
dispatcher.register(userTyping);

String botToken = System.getenv("SLACK_BOT_TOKEN");
Slack slack = Slack.getInstance();

// Initialize the client with a valid WSS URL
RTMClient rtm = slack.rtmConnect(botToken);
// Establish a WebSocket connection and start subscribing Slack events
rtm.connect();

// Enable an event dispatcher
rtm.addMessageHandler(dispatcher.toMessageHandler());

// Deregister a event handler runtime
dispatcher.deregister(userTyping);

// Send messages over a WebSocket connection
String channelId = "C1234567";
String message = Message.builder().id(1234567L).channel(channelId).text(":wave: Hi there!").build().toJSONString();
rtm.sendMessage(message);

// To subscribe "presence_change" events
String userId = "U1234567";
String presenceQuery = PresenceQuery.builder().ids(Arrays.asList(userId)).build().toJSONString();
rtm.sendMessage(presenceQuery);
String presenceSub = PresenceSub.builder().ids(Arrays.asList(userId)).build().toJSONString();
rtm.sendMessage(presenceSub);

// A bit heavy-weight operation to re-establish a WS connection for sure
// Don't call this method frequently - it will result in a rate-limited error
rtm.reconnect();

// Disconnect from Slack - #close() method does the same
rtm.disconnect();
```
