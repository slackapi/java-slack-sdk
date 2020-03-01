---
layout: default
title: "Events API"
lang: en
---

# Events API

The [Events API](https://api.slack.com/events-api) is a streamlined, easy way to build apps and bots that respond to activities in Slack. All you need is a Slack app and a secure place for us to send your events.

### Slack App Configuration

To enable Events API, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Event Subscriptions** on the left pain. There are a few things to do on the page.

* Turn on **Enable Events**
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events`
* Add subscriptions to bot events
  * Click **Subscribe to bot events**
  * Click **Add Bot User Event** button
  * Choose events to subscrible 
* Click the **Save Changes** button at the bottom for sure

### What Your Bolt App Does

All you need to do to handle Events API requests are:

1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body and check if the `type` in `event` is the one you'd like to handle
1. Whatever you want to do with the event data
1. Respond to the Slack API server with 200 OK as an acknowledgment

Your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the Slack Platform may retry after a while.

## Examples

**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), first.

Bolt does many of the commonly required tasks for you. The steps you need to handle would be:

* Specify the `event.type` (and also `event.subtype` [when necessary](https://api.slack.com/events/message#message_subtypes)) to handle
* Whatever you want to do with the event data
* Call `ack()` as an acknowledgment

In event payloads, `response_url` is not included as it's not a payload coming from direct user interactions. Also, it's not possible to post a message using `ctx.ack()` for the same reason. If an event you receive is a user interaction and you'd like to post a reply to the user at the conversation the event happened, call [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) method or other similar ones with `channel` in the event payload.

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
      ctx.logger.error("chat.postMessage failed: {}", message.getError());
    }
  }
  return ctx.ack();
});
```

The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)

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
      ctx.logger.error("chat.postMessage failed: ${message.error}")
    }
  }
  ctx.ack()
}
```

Here is another example. Although Bolt for Java hasn't provided something similar to Bolt for JavaScript's `app.message` handler yet, it's pretty easy to implement it just using `message` event handler as below.

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
      ctx.logger.error("reactions.add failed: {}", reaction.getError());
    }

    // Send the message to the SDK author
    ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
    if (permalink.isOk()) {
      ChatPostMessageResponse message = client.chatPostMessage(r -> r
        .channel(notificationChannelId)
        .text("An issue with the Java SDK might be reported:\n" + permalink.getPermalink())
        .unfurlLinks(true));
      if (!message.isOk()) {
        ctx.logger.error("chat.postMessage failed: {}", message.getError());
      }
    } else {
      ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
    }
  }
  return ctx.ack();
});
```

### Under the Hood

If you hope to understand what is actually happening with the above code, reading  the following (a bit pseudo) code may be helpful.

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  // This needs "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }
  // 2. Parse the request body and check if the `type` in `event` is the one you'd like to handle
  // The request body in JSON format
  String payloadString = request.getBodyAsString();
  String eventType = PseudoEventTypeExtractor.extract(payloadString);
  if (eventType != null && eventType.equals("message")) {
    Gson gson = GsonFactory.createSnakeCase();
    MessagePayload payload = gson.fromJson(payloadString, MessagePayload.class);
    // 3. Whatever you want to do with the event data
  } else {
    // other patterns
    return PseudoHttpResponse.builder().status(404).build();
  }
  // 4. Respond to the Slack API server with 200 OK as an acknowledgment
  return PseudoHttpResponse.builder().status(200).build();
}
```
