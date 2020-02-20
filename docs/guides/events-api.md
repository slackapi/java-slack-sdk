---
layout: default
title: "Events API"
lang: en
---

# Events API

The [Events API](https://api.slack.com/events-api) used in the following example is a streamlined, easy way to build apps and bots that respond to activities in Slack. All you need is a Slack app and a secure place for us to send your events.

All you need to do to handle Events API requests are:

1. Verify requests from Slack (read [this](https://api.slack.com/docs/verifying-requests-from-slack) if unfamiliar)
1. Parse the request body and check if the `type` in `event` is the one you'd like to handle
1. Whatever you want to do with the event data
1. Respond with 200 OK reply as aknowledgement


Your app has to respond to the request within 3 seconds by `ack()` method for sure. Otherwise, the user will see the timeout error on Slack.

## Bolt Examples

**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [The Basics of Bolt]({{ site.url | append: site.baseurl }}/guides/Bolt), first.

Bolt does most of the things for you. The steps you need to handle would be:

* Specify the `event.type` (and also `event.subtype` [when necessary](https://api.slack.com/events/message#message_subtypes)) to handle
* Whatever you want to do with the event data
* Respond with 200 OK as the acknowledgment

In event payloads, `response_url` is not included as it's not a payload coming from direct user interactions. Also, it's not possible to post a message using `ctx.ack()` for the same reason. If an event you receive is a user interaction and you'd like to post a reply to the user at the conversation the event happened, call **chat.postMessage** API or other similar ones with `channel` in the event payload.

```java
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.event.ReactionAddedEvent;

app.event(ReactionAddedEvent.class, (payload, ctx) -> {
  ReactionAddedEvent event = payload.getEvent();
  if (event.getReaction().equals("white_check_mark")) {
    ChatPostMessageResponse message = ctx.client().chatPostMessage(r -> r
      .channel(event.getItem().getChannel())
      .threadTs(event.getItem().getTs())
      .text("<@" + event.getUser() + "> Thank you! We greatly appreciate your efforts :two_hearts:"));
    if (!message.isOk()) {
      logger.error("chat.postMessage failed: {}", message.getError());
    }
  }
  return ctx.ack();
});
```

The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/Bolt#getting-started-in-kotlin) may be helpful)

```kotlin
app.event(ReactionAddedEvent::class.java) { payload, ctx ->
  val event = payload.event
  if (event.reaction == "white_check_mark") {
    val message = ctx.client().chatPostMessage {
      it.channel(event.item.channel)
        .threadTs(event.item.ts)
        .text("<@${event.user}> Thank you! We greatly appreciate your efforts :two_hearts:")
    }
    if (!message.isOk) {
      logger.error("chat.postMessage failed: ${message.error}")
    }
  }
  ctx.ack()
}
```

Here is another example. Although Bolt doesn't offer something similar to Bolt's `app.message` handler, it's pretty easy to implement it just using `message` event handler as below.

```java
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.model.event.MessageEvent;

import java.util.Arrays;
import java.util.regex.Pattern;

String notificationChannelId = "D1234567";
Pattern sdk = Pattern.compile(".*[(Java SDK)|(Bolt)|(slack\\-java\\-sdk)].*", Pattern.CASE_INSENSITIVE);
Pattern issues = Pattern.compile(".*[(bug)|(t work)|(issue)|(support)].*", Pattern.CASE_INSENSITIVE);

app.event(MessageEvent.class, (payload, ctx) -> {
  MessageEvent event = payload.getEvent();
  String text = event.getText();
  // check if the message contains some monitoring keywords
  if (sdk.matcher(text).matches() && issues.matcher(text).matches()) {

    MethodsClient client = ctx.client();

    // Add 👀reacji to the message
    String channelId = event.getChannel();
    String ts = event.getTs();
    ReactionsAddResponse reaction = client.reactionsAdd(r -> r.channel(channelId).timestamp(ts).name("eyes"));
    if (!reaction.isOk()) {
      log.error("reactions.add failed: {}", reaction.getError());
    }

    // Send the message to the SDK author
    ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
    if (permalink.isOk()) {
      ChatPostMessageResponse message = client.chatPostMessage(r -> r
        .channel(notificationChannelId)
        .text("An issue with the Java SDK might be reported:\n" + permalink.getPermalink())
        .unfurlLinks(true));
      if (!message.isOk()) {
        log.error("chat.postMessage failed: {}", message.getError());
      }
    } else {
      log.error("chat.getPermalink failed: {}", permalink.getError());
    }
  }
  return ctx.ack();
});
```

## Under the Hood

If you hope to understand what is actually happening with the above code, reading  the following (a bit psuedo) code may be helpful.

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  //   "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }
  // 2. Parse the request body and check if the `type` in `event` is the one you'd like to handle
  // The request body in JSON format
  String payloadString = request.getBodyAsString();
  String eventType = PseudoEventTypeExtractor.extract(payloadString);
  Gson gson = GsonFactory.createSnakeCase();
  if (eventType != null && eventType.equals("message")) {
    MessagePayload payload = gson.fromJson(payloadString, MessagePayload.class);
    // 3. Whatever you want to do with the event data
  } else {
    // other patterns
    return PseudoHttpResponse.builder().status(404).build();
  }
  // 4. Respond with 200 OK reply as aknowledgement
  return PseudoHttpResponse.builder().status(200).build();
}
```
