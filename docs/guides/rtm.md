---
layout: default
title: "Real Time Messaging (RTM)"
lang: en
---

# Real Time Messaging (RTM)

[The Real Time Messaging API](https://api.slack.com/rtm) is a WebSocket-based API that allows you to receive events from Slack in real-time and send messages as users. It's sometimes referred to just as the "RTM API".

**NOTE**: RTM isn't available for modern scoped apps anymore. We recommend using the [Events API]({{ site.url | append: site.baseurl }}/guides/events-api) and [Web API]({{ site.url | append: site.baseurl }}/guides/web-api-basics) instead. If you need to use RTM (possibly due to corporate firewall limitations), you can do so by creating a legacy scoped app. If you have an existing RTM app, do not update its scopes as it will be updated to a modern scoped app and stop working with RTM.

## Subscribing Slack Events Over WebSocket

Here is a minimum working example demonstrating how event handlers work.

```java
import com.slack.api.Slack;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.*;

// Dispatches incoming message events from RTM API
RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
RTMEventHandler<UserTypingEvent> userTyping = new RTMEventHandler<UserTypingEvent>() {
  @Override
  public void handle(UserTypingEvent event) {
    // do something here
  }
};
dispatcher.register(userTyping);

String botToken = System.getenv("SLACK_BOT_TOKEN");
Slack slack = Slack.getInstance();

// Establish a WebSocket connection and start subscribing Slack events
RTMClient rtm = slack.rtmConnect(botToken);

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
rtm.reconnect();

// Disconnect from Slack - #close() method does the same
rtm.disconnect();
```