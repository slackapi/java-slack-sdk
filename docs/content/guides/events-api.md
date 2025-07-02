---
lang: en
---

# Events

The [Events API](https://docs.slack.dev/apis/events-api/) is a streamlined way to build apps and bots that respond to activities in Slack. All you need is a Slack app and a secure place for us to send your events.

### Slack app configuration

To enable the Events API, visit the [Slack app settings page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Event Subscriptions** on the left pane. There are a few things to do on the page.

* Turn on **Enable Events**
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events` (this step is not required for Socket Mode apps)
* Add subscriptions to bot events
  * Click **Subscribe to bot events**
  * Click **Add Bot User Event** button
  * Choose events to subscribe to
* Click the **Save Changes** button at the bottom for sure

### What your Bolt app does

To handle Events API requests, do the following:

1. [Verify requests](https://docs.slack.dev/authentication/verifying-requests-from-slack) from Slack
1. Parse the request body and check if the `type` in `event` is the one you'd like to handle
1. Code the desired logic you want to do with the event data
1. Respond to the Slack API server with `200 OK` as an acknowledgment

Your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the Slack Platform may retry.

---
## Examples

:::tip[Tip]

If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt](/guides/getting-started-with-bolt) first.

:::

Bolt does many of the commonly required tasks for you. The steps you need to handle are:

* Specify [the Java class](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model/sdkLatestVersion/slack-api-model-sdkLatestVersion-javadoc.jar/!/com/slack/api/model/event/Event.html) corresponding to `event.type` (and also `event.subtype` [when necessary](https://docs.slack.dev/reference/events/message)) to handle
* Code the desired logic you want to do with the event data
* Call `ack()` as an acknowledgment

In event payloads, `response_url` is not included as it's not a payload coming from direct user interactions. Also, it's not possible to post a message using `ctx.ack()` for the same reason. If an event you receive is a user interaction and you'd like to post a reply to the user in the conversation where the event happened, call the [`chat.postMessage`](https://docs.slack.dev/reference/methods/chat.postmessage) method with `channel` in the event payload.

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

The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin](/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful.)

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

Here is another example. With an `app.message` listener, you can receive only the events that contains given keyword or regular expressions, and do something with those event data in a fewer lines of code.

```java
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.model.event.MessageEvent;

import java.util.Arrays;
import java.util.regex.Pattern;

String notificationChannelId = "D1234567";

// check if the message contains some monitoring keywords
Pattern sdk = Pattern.compile(".*[(Java SDK)|(Bolt)|(slack\\-java\\-sdk)].*", Pattern.CASE_INSENSITIVE);
app.message(sdk, (payload, ctx) -> {
  MessageEvent event = payload.getEvent();
  String text = event.getText();
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
      .text("The Java SDK might be mentioned:\n" + permalink.getPermalink())
      .unfurlLinks(true));
    if (!message.isOk()) {
      ctx.logger.error("chat.postMessage failed: {}", message.getError());
    }
  } else {
    ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
  }
  return ctx.ack();
});
```

If matching an exact word in a text message works for you, the code looks much simpler as below.

```java
app.message(":wave:", (payload, ctx) -> {
  ctx.say("Hello, <@" + payload.getEvent().getUser() + ">");
  return ctx.ack();
});
```

## Under the hood

If you hope to understand what is happening with the above code, reading the following (pseudo) code may be helpful.

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.events.*;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://docs.slack.dev/authentication/verifying-requests-from-slack
  // This needs "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }
  // 2. Parse the request body and check if the `type` in `event` is the one you'd like to handle
  // The request body in JSON format
  String payloadString = request.getBodyAsString();
  EventTypeExtractor eventTypeExtractor = new EventsDispatcherImpl();
  String eventType = eventTypeExtractor.extractEventType(payloadString);
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
