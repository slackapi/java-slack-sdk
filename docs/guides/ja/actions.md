---
layout: ja
title: "アクション"
lang: ja
---

# アクション

[Actions](https://api.slack.com/interactivity/actions) are simple shortcuts that people can use to quickly complete a task with your app — like reporting a bug, requesting time off, or starting a meeting.

As of January 2020, this SDK supports the followings (yes, there are [more to come soon](https://medium.com/slack-developer-blog/introducing-the-slack-app-toolkit-3d509a15f41b)!).

* [Message Actions](https://api.slack.com/interactive-messages)

For all types of actions requests, your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the user will see the timeout error on Slack.

## Message Actions

You can add new message actions from **Interactive Components > Actions** on your Slack App admin pages. The specified **Callback ID** will be sent as `callback_id` in payloads from Slack.

<img src="{{ site.url | append: site.baseurl }}/assets/images/bolt-actions.png" width="400" />

All you need to do to handle message actions requests are:

1. Verify requests from Slack (read [this](https://api.slack.com/docs/verifying-requests-from-slack) if unfamiliar)
1. Parse the request body and check if the `callback_id` is the one you'd like to handle
1. Build a reply message or do whatever you want to do
1. Respond with 200 OK reply as acknowledgement

### Examples

**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), first.

Bolt does most of the things for you. The steps you need to handle would be:

* Specify the `callback_id` to handle (by either of the exact name or regular expression)
* Build a reply message or do whatever you want to do
* Respond with 200 OK reply as aknowledgement

Message actions request payloads have `request_url`, so that your app can reply to the action (even asynchronously after the aknowledgement). If you post a message using `response_url`, call `ctx.ack()` without arguments and use `ctx.respond()` to post a message.

Here is a tiny example demonstrating how to handle message actions requests in a Bolt app.

```java
import com.slack.api.model.Message;
import com.slack.api.model.view.View;
import com.slack.api.methods.response.views.ViewsOpenResponse;

app.messageAction("create-task-action-callback-id", (req, ctx) -> {
  String userId = req.getPayload().getUser().getId();
  Message message = req.getPayload().getMessage();
  // do something with the message

  ViewsOpenResponse viewsOpenResp = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView(message)));
  if (!viewsOpenResp.isOk()) {
    String errorCode = viewsOpenResp.getError();
    logger.error("Failed to open a modal view for user: {} - error: {}", userId, errorCode);
    ctx.respond(":x: Failed to open a modal view because of " + errorCode);
  }

  return ctx.ack(); // respond with 200 OK to the request
});

View buildView(Message message) {
  return null; // TODO
}
```

The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)

```kotlin
app.messageAction("create-task-action-callback-id") { req, ctx ->
  val userId = req.payload.user.id
  val message = req.payload.message
  // do something with the message

  val viewsOpenResp = ctx.client().viewsOpen {
    it.triggerId(ctx.triggerId)
      .view(buildView(message))
  }
  if (!viewsOpenResp.isOk) {
    val errorCode = viewsOpenResp.error
    logger.error("Failed to open a modal view for user: ${userId} - error: ${errorCode}")
    ctx.respond(":x: Failed to open a modal view because of ${errorCode}")
  }

  ctx.ack() // respond with 200 OK to the request
}
```

## Under the Hood

If you hope to understand what is actually happening with the above code, reading  the following (a bit psuedo) code may be helpful.

```java
import java.util.Map;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  //   "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. Parse the request body and check if the `callback_id` is the one you'd like to handle
  MessageActionPayload payload = GsonFactory.createSnakeCase().fromJson(requestBody, MessageActionPayload.class);
  if (payload.getCallbackId().equals("create-task-action-callback-id")) {
    // 3. Build a reply message or do whatever you want to do
  }

  // 4. Respond with 200 OK reply as aknowledgement
  return PseudoHttpResponse.builder().status(200).build();
}
```
