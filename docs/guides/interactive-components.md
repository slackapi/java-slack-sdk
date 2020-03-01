---
layout: default
title: "Interactive Components"
lang: en
---

# Interactive Components

[Interactive components](https://api.slack.com/reference/block-kit/interactive-components) are a subset of [Block Kit](https://api.slack.com/block-kit) elements that add interactivity to various [surfaces](https://api.slack.com/surfaces). Interactions on blocks may happen not only in messages but also on [Modals]({{ site.url | append: site.baseurl }}/guides/modals), and [Home tabs]({{ site.url | append: site.baseurl }}/guides/app-home).

Reading [Composing Messages]({{ site.url | append: site.baseurl }}/guides/composing-messages) is helpful to learn how to build [Block Kit](https://api.slack.com/block-kit) messages with this SDK.

### Slack App Configuration

To enable Interactive Components, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Interactive Components** on the left pain. There are three things to do on the page.

* Turn on the feature
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events`
* Click the **Save Changes** button at the bottom for sure

### What Your Bolt App Does

All your app needs to do to handle Slack requests by user interactions are:

1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body and check if the `action_id` in a block is the one you'd like to handle
1. Build a reply message or surface to interact with the user further
1. Respond to the Slack API server with 200 OK as an acknowledgment

Your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the user will see the timeout error on Slack. For some of the requests including external selects, having valid parameters to the method may be required.

## Examples

**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), first.

Bolt does many of the commonly required tasks for you. The steps you need to handle would be:

* Specify the `action_id` to handle (by either of the exact name or regular expression)
* Build a reply message or surface to interact with the user further
* Call `ack()` as an acknowledgment

The request payloads have `request_url`, so that your app can reply to the action (even asynchronously after the acknowledgment). The URL is usable up to 5 times within 30 minutes of the action invocation. If you post a message using `response_url`, call `ctx.ack()` without arguments and use `ctx.respond()` to post a message.

Let's say, a message has a simple `"actions"`-typed block that has a button.

```javascript
{
  "type": "actions",
  "elements": [{
    "type": "button",
    "action_id": "button-action",
    "text": { "type": "plain_text", "text": "Button", "emoji": true },
    "value": "button's value"
  }]
}
```

When a user clicks the button, `"block_actions"`-typed event with the `action_id` the element with the value `"button-action"` has will come to your Bolt app.

```java
// when a user clicks a button in the actions block
app.blockAction("button-action", (req, ctx) -> {
  String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
  if (req.getPayload().getResponseUrl() != null) {
    // Post a message to the same channel if it's a block in a message
    ctx.respond("You've sent " + value + " by clicking the button!");
  }
  return ctx.ack();
});
```

The sample code in Kotlin looks like as blow.

```kotlin
app.blockAction("button-action") { req, ctx ->
  val value = req.payload.actions[0].value
  if (req.payload.responseUrl != null) {
    ctx.respond("You've sent ${value} by clicking the button!")
  }
  ctx.ack()
}
```

Here is another example. This is a [select menu using external data source](https://api.slack.com/reference/block-kit/block-elements#external_select).

```javascript
{
  "block_id": "topics",
  "type": "section",
  "text": { "type": "mrkdwn", "text": "Select the meeting topics" },
  "accessory": {
    "action_id": "topics-action",
    "type": "multi_external_select",
    "min_query_length": 1,
    "placeholder": { "type": "plain_text", "text": "Select", "emoji": true }
  }
}
```

With this type of select menu, your app is expected to handle both `block_suggestion` and `block_actions` coming from the element named `topics-action`.

```java
import com.slack.api.app_backend.interactive_components.response.Option;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

final List<Option> allOptions = Arrays.asList(
  new Option(plainText("Schedule", true), "schedule"),
  new Option(plainText("Budget", true), "budget"),
  new Option(plainText("Assignment", true), "assignment")
);

// when a user enters some word in "Topics"
app.blockSuggestion("topics-action", (req, ctx) -> {
  String keyword = req.getPayload().getValue();
  List<Option> options = allOptions.stream()
    .filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword))
    .collect(toList());
  return ctx.ack(r -> r.options(options.isEmpty() ? allOptions : options));
});

// when a user chooses an item from the "Topics"
app.blockAction("topics-action", (req, ctx) -> {
  return ctx.ack();
});
```

The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)

```kotlin
import com.slack.api.app_backend.interactive_components.response.Option
import com.slack.api.model.block.composition.BlockCompositions.plainText // static import

val allOptions = listOf(
  Option(plainText("Schedule", true), "schedule"),
  Option(plainText("Budget", true), "budget"),
  Option(plainText("Assignment", true), "assignment")
)

// when a user enters some word in "Topics"
app.blockSuggestion("topics-action") { req, ctx ->
  val keyword = req.payload.value
  val options = allOptions.filter { (it.text as PlainTextObject).text.contains(keyword) }
  ctx.ack { it.options(if (options.isEmpty()) allOptions else options) }
}
// when a user chooses an item from the "Topics"
app.blockAction("topics-action") { req, ctx ->
  ctx.ack()
}
```

### Under the Hood

If you hope to understand what is actually happening with the above code, reading the following (a bit pseudo) code may be helpful.

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  //   "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. Parse the request body and check if the `action_id` in a block is the one you'd like to handle

  // payload=url-encoded-json-string in the request body
  String payloadString = PseudoPayloadExtractor.extract(request.getBodyAsString());
  // The value looks like: { "type": "block_actions", "team": { "id": "T1234567", ... 
  String payloadType = PseudoActionTypeExtractor.extract(payloadString);
  
  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType != null && payloadType.equals("block_actions")) {
    BlockActionPayload payload = gson.fromJson(payloadString, BlockActionPayload.class);
    if (payload.getActionId().equals("topics-action")) {
      // 3. Build a reply message or surface to interact with the user further
    }
  } else if (payloadType != null && payloadType.equals("block_suggestion")) {
    BlockSuggestionPayload payload = gson.fromJson(payloadString, BlockSuggestionPayload.class);
    if (payload.getActionId().equals("topics-action")) {
      List<Option> options = buildOptions(payload.getValue());
      // Return a successful response having `options` in its body
      return PseudoHttpResponse.builder().body(Map.of("options", options)).status(200).build();
    }
  } else {
    // other patterns
    return PseudoHttpResponse.builder().status(404).build();
  }
  // 4. Respond to the Slack API server with 200 OK as an acknowledgment
  return PseudoHttpResponse.builder().status(200).build();
}
```
