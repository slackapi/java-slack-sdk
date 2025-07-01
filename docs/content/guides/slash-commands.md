---
lang: en
---

# Slash Commands

[Slash Commands](https://docs.slack.dev/interactivity/implementing-slash-commands) allow users to invoke your app from the message composer box.

Responding to slash command invocations is a common use case. Your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the user will see the timeout error on Slack.

## Slack app configuration

To enable slash commands, visit the [Slack app settings page](http://api.slack.com/apps), choose the app you're working on, go to **Features** > **Slash Commands** on the left pane. There are a few things to do there on the page.

* Click **Create New Command** button
* Input the command information on the dialog:
  * **Command**: `/hello`
  * **Request URL**: `https://{your domain here}/slack/events` - if you use ngrok for development, the URL would be `https://{random}.ngrok.io/slack/events` (this step is not required for Socket Mode apps)
  * **Short Description**: whatever you like
* Click **Save** Button

## What your Bolt app does

To handle slash command requests:

1. [Verify requests](https://docs.slack.dev/authentication/verifying-requests-from-slack) from Slack
1. Parse the request body and check if the `command` is the one you'd like to handle
1. Build a reply message or do whatever you want to do
1. Respond to the Slack API server with `200 OK` as an acknowledgment

If the response body is empty, the response will be recognized as just an acknowledgment. No message will be posted to the channel.

---
## Examples

:::tip[Tip]

If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt](/guides/getting-started-with-bolt) first.

::::

Bolt does many of the commonly required tasks for you. The steps you need to handle would be:

* Specify the `command` to handle (by either of the exact name or regular expression)
* Build a reply message or do whatever you want to do
* Call `ack()` as an acknowledgment

Slash command request payloads have a `response_url`, so that your app can reply to the action (even asynchronously after the acknowledgment). If you post a message using a `response_url`, call `ctx.ack()` without arguments and use `ctx.respond()` to post a message.

Here is an example demonstrating how to handle slash command requests in a Bolt app.

```java
app.command("/echo", (req, ctx) -> {
  String commandArgText = req.getPayload().getText();
  String channelId = req.getPayload().getChannelId();
  String channelName = req.getPayload().getChannelName();
  String text = "You said " + commandArgText + " at <#" + channelId + "|" + channelName + ">";
  return ctx.ack(text); // respond with 200 OK
});
```

Here is an example to use a `response_url` for posting a message. You can also asynchronously run `ctx.respond` after the acknowledgment.

```java
app.command("/echo", (req, ctx) -> {
  String text = buildMessage(req);
  ctx.respond(text); // perform an HTTP request
  return ctx.ack(); // respond with 200 OK
});
```

The same code in Kotlin looks like this. (New to Kotlin? [Getting Started in Kotlin](/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful.)

```kotlin
app.command("/echo") { req, ctx ->
  val commandArgText = req.payload.text
  val channelId = req.payload.channelId
  val channelName = req.payload.channelName
  val text = "You said ${commandArgText} at <#${channelId}|${channelName}>"
  ctx.ack(text)
}

app.command("/echo") { req, ctx ->
  val text = buildMessage(req)
  ctx.respond(text) // send via response_url
  ctx.ack()
}
```

To learn how to build [Block Kit](https://docs.slack.dev/block-kit/) messages with this SDK, consult [Composing Messages](/guides/composing-messages).

## Under the hood

If you hope to understand what is happening with the above code, reading the following (a bit pseudo) code may be helpful.

```java
import java.util.Map;
import com.slack.api.Slack;
import com.slack.api.app_backend.slash_commands.*;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://docs.slack.dev/authentication/verifying-requests-from-slack
  // This needs "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. Parse the request body and check if the `command` is the one you'd like to handle
  SlashCommandPayloadParser parser = new SlashCommandPayloadParser();

  // The request body looks like this:
  //   token=gIkuvaNzQIHg97ATvDxqgjtO&team_id=T0001&team_domain=example
  //   &enterprise_id=E0001&enterprise_name=Globular%20Construct%20Inc
  //   &channel_id=C2147483705&channel_name=test
  //   &user_id=U2147483697&user_name=Steve
  //   &command=weather&text=94070&response_url=https://hooks.slack.com/commands/1234/5678
  //   &trigger_id=123.123.123
  String requestBody = request.getBodyAsString();

  SlashCommandPayload payload = parser.parse(requestBody);

  if (payload.getCommand().equals("/echo")) {
    // 3. Build a reply message or do whatever you want to do
    String commandArgText = payload.getText();
    String channelId = payload.getChannelId();
    String channelName = payload.getChannelName();
    String text = "You said " + commandArgText + " at <#" + channelId + "|" + channelName + ">";

    // 4. Respond to the Slack API server with 200 OK as an acknowledgment
    return PseudoHttpResponse.builder()
      .status(200)
      .body(PseudoJsonOps.serialize(Map.of("text", text))) // send a reply in the response
      .build();
  } else {
    return PseudoHttpResponse.builder().status(404).build();
  }
}
```

Also, Bolt's `ctx.respond` method internally does messaging via `response_url` like this.

```java
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.webhook.WebhookResponse;

// Reply to the request using response_url
Slack slack = Slack.getInstance();
SlashCommandResponseSender responder = new SlashCommandResponseSender(slack);
SlashCommandResponse reply = SlashCommandResponse.builder().text(text).build();
WebhookResponse result = responder.send(payload.getResponseUrl(), reply);
```
