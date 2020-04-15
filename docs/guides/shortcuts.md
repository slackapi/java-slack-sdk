---
layout: default
title: "Shortcuts"
lang: en
---

# Shortcuts

**Shortcuts** are simple entry points for users to invoke your app. [**Global shortcuts**](https://api.slack.com/interactivity/shortcuts/using#global_shortcuts) are surfaced from everywhere in Slack, while [**message shortcuts**](https://api.slack.com/interactivity/shortcuts/using#message_shortcuts) are surfaced in the message context menu.

Your app has 3 seconds to call `ack()`, which acknowledges a shortcut request is received from Slack.

## Global and Message Shortcuts

### Slack App Configuration

To enable shortcuts, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Interactivity & Shortcuts** on the left pane. There are four things to do on the page.

* Turn on the feature
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events`
* Add/edit shortcuts in the **Shortcuts** section
  * **Name**, **Short Description**, **Callback ID**
* Click the **Save Changes** button at the bottom for sure

The specified **Callback ID** will be sent as `callback_id` in payloads from Slack.

### What Your Bolt App Does

All your app needs to do to handle message shortcuts requests are:

1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body and check if the `callback_id` is the one you'd like to handle
1. Build a reply message or do whatever you want to do
1. Respond to the Slack API server with 200 OK as an acknowledgment

## Examples

**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), first.

Bolt does many of the commonly required tasks for you. The steps you need to handle would be:

* Specify the `callback_id` to handle (by either of the exact name or regular expression)
* Build a reply message or do whatever you want to do
* Call `ack()` as an acknowledgment

Message shortcut request payloads have `response_url`, so that your app can reply to the shortcut (even asynchronously after the acknowledgment). The URL is usable up to 5 times within 30 minutes of the shortcut invocation. If you post a message using `response_url`, call `ctx.ack()` without arguments and use `ctx.respond()` to post a message.

Global shortcut request payloads don't have `response_url` by default. If you have an `input` block asking users a channel to post a message, global shortcut request payloads may provide `response_urls`. To enable this, set the block element type as either [`channels_select`](https://api.slack.com/reference/block-kit/block-elements#channel_select) or [`conversations_select`](https://api.slack.com/reference/block-kit/block-elements#conversation_select) and add `"response_url_enabled": true`.

Here is a tiny example demonstrating how to handle shortcut requests in a Bolt app.

```java
import com.slack.api.model.Message;
import com.slack.api.model.view.View;
import com.slack.api.methods.response.views.ViewsOpenResponse;

// Handles global shortcut requests
app.globalShortcut("create-task-shortcut-callback-id", (req, ctx) -> {
  // do something with the payload
  ViewsOpenResponse viewsOpenResp = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView()));

  return ctx.ack(); // respond with 200 OK to the request
});

// Handles message shortcut requests (formerly message actions)
app.messageShortcut("create-task-shortcut-callback-id", (req, ctx) -> {
  String userId = req.getPayload().getUser().getId();
  Message message = req.getPayload().getMessage();
  // do something with the message

  ViewsOpenResponse viewsOpenResp = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView(message)));
  if (!viewsOpenResp.isOk()) {
    String errorCode = viewsOpenResp.getError();
    ctx.logger.error("Failed to open a modal view for user: {} - error: {}", userId, errorCode);
    ctx.respond(":x: Failed to open a modal view because of " + errorCode);
  }

  return ctx.ack(); // respond with 200 OK to the request
});

View buildView(Message message) { return null; }
View buildView() { return null; }
```

The followings are the ones written in Kotlin. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)

```kotlin
// Handles global shortcut requests
app.globalShortcut("create-task-shortcut-callback-id") { req, ctx -> 
  // do something with the payload
  val viewsOpenResp = ctx.client().viewsOpen {
    it.triggerId(ctx.triggerId)
      .view(buildView()))
  }

  ctx.ack() // respond with 200 OK to the request
}

// Handles message shortcut requests (formerly message actions)
app.messageShortcut("create-task-shortcut-callback-id") { req, ctx ->
  val userId = req.payload.user.id
  val message = req.payload.message
  // do something with the message

  val viewsOpenResp = ctx.client().viewsOpen {
    it.triggerId(ctx.triggerId)
      .view(buildView(message))
  }
  if (!viewsOpenResp.isOk) {
    val errorCode = viewsOpenResp.error
    ctx.logger.error("Failed to open a modal view for user: ${userId} - error: ${errorCode}")
    ctx.respond(":x: Failed to open a modal view because of ${errorCode}")
  }

  ctx.ack() // respond with 200 OK to the request
}
```

### Under the Hood

If you hope to understand what is actually happening with the above code, reading the following (a bit pseudo) code may be helpful.

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.MessageShortcutPayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  // This needs "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. Parse the request body and check if the `callback_id` is the one you'd like to handle

  // payload={URL-encoded JSON} in the request body
  String payloadString = PseudoPayloadExtractor.extract(request.getBodyAsString());
  // The value looks like: { "type": "shortcut", "team": { "id": "T1234567", ... 
  String payloadType = PseudoActionTypeExtractor.extract(payloadString);

  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType.equals("shortcut")) {
    GlobalShortcutPayload payload = gson.fromJson(payloadString, GlobalShortcutPayload.class);
    if (payload.getCallbackId().equals("create-task-shortcut-callback-id")) {
      // 3. Build a reply message or do whatever you want to do
    }
  } else if (payloadType.equals("message_action")) {
    MessageShortcutPayload payload = gson.fromJson(payloadString, MessageShortcutPayload.class);
    if (payload.getCallbackId().equals("create-task-shortcut-callback-id")) {
      // 3. Build a reply message or do whatever you want to do
    }
  } else {
    // other patterns
    return PseudoHttpResponse.builder().status(404).build();
  }

  // 4. Respond to the Slack API server with 200 OK as an acknowledgment
  return PseudoHttpResponse.builder().status(200).build();
}
```
