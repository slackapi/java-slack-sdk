---
layout: default
title: "Modals"
lang: en
---
# Modals
[Modals](https://api.slack.com/surfaces/modals/using) are a focused surface to collect data from users or display dynamic and interactive information. To users, modals appear as focused surfaces inside of Slack enabling brief, yet deep interactions with apps. Modals can be assembled using the visual and interactive components found in [Block Kit](https://api.slack.com/block-kit).
### Slack App Configuration
The first step to use modals in your app is to enable Interactive Components. Visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Interactivity & Shortcuts** on the left pane. There are three things to do on the page.
* Turn on the feature
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events`
* Click the **Save Changes** button at the bottom for sure
### What Your Bolt App Does
There are three patterns to handle on modals. As always, your app has to respond to the request within 3 seconds by `ack()` method. Otherwise, the user will see the timeout error on Slack.
#### `"block_actions"` requests
When someone uses an [interactive component](https://api.slack.com/reference/block-kit/interactive-components) in your app's modal views, the app will receive a [block_actions payload](https://api.slack.com/reference/interaction-payloads/block-actions). All you need to do to handle the `"block_actions"` requests are:
1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body, and check if the `type` is `"block_actions"` and the `action_id` in a block is the one you'd like to handle
1. [Modify/push a view via API](https://api.slack.com/surfaces/modals/using#modifying) and/or update the modal to hold the sent data as [private_metadata](https://api.slack.com/surfaces/modals/using#carrying_data_between_views)
1. Respond to the Slack API server with 200 OK as an acknowledgment
#### `"view_submission"` requests
When a modal view is submitted, you'll receive a [view_submission payload](https://api.slack.com/reference/interaction-payloads/views#view_submission). All you need to do to handle the `"view_submission"` requests are:
1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body, and check if the `type` is `"view_submission"` and the `callback_id` is the one you'd like to handle
1. Extract the form data from `view.state.values`
1. Do whatever to do such as input validations, storing them in a database, talking to external services
1. Respond with 200 OK as the acknowledgment by either of the followings:
  * Sending an empty body means closing only the modal
  * Sending a body with `response_action` (possible values are `"errors"`, `"update"`, `"push"`, `"clear"`)
#### `"view_closed"` requests (only when `notify_on_close` is `true`)
Your app can optionally receive [view_closed payloads](https://api.slack.com/reference/interaction-payloads/views#view_closed) whenever a user clicks on the Cancel or x buttons. These buttons are standard, not blocks, in all app modals. To receive the `"view_closed"` payload when this happens, set `notify_on_close` to `true` when creating a view with [views.open](https://api.slack.com/methods/views.open) and [views.push](https://api.slack.com/methods/views.push) methods.
All you need to do to handle the `"view_closed"` requests are:
1. [Verify requests](https://api.slack.com/docs/verifying-requests-from-slack) from Slack
1. Parse the request body, and check if the `type` is `"view_closed"` and the `callback_id` is the one you'd like to handle
1. Do whatever to do at the timing
1. Respond with 200 OK as the acknowledgment
### Modal Development Tips
In general, there are a few things to know when working with modals. They would be:
* You need `trigger_id` in user interaction payloads to open a modal view
* Only the inputs in `"type": "input"` blocks will be included in `view_submission`'s `view.state.values`
* Each input/selection in non-`"input"` typed blocks such as `"section"`, `"actions"` is sent as a `"block_actions"` request
* You use `callback_id` to identify a modal, a pair of `block_id` and `action_id` to identify each input in `view.state.values`
* You can use `view.private_metadata` to hold the internal state and/or `"block_actions"` results on the modal
* You respond to `"view_submission"` requests with `response_action` to determine the next state of a modal
* [views.update](https://api.slack.com/methods/views.update) and [views.push](https://api.slack.com/methods/views.push) API methods are supposed to be used when receiving `"block_actions"` requests in modals, NOT for `"view_submission"` requests
---
## Examples
**NOTE**: If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), first.
Let's start with opening a modal. Let's say, we're going to open the following modal.
```javascript
{
  "type": "modal",
  "callback_id": "meeting-arrangement",
  "notify_on_close": true,
  "title": { "type": "plain_text", "text": "Meeting Arrangement" },
  "submit": { "type": "plain_text", "text": "Submit" },
  "close": { "type": "plain_text", "text": "Cancel" },
  "private_metadata": "{\"response_url\":\"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\"}",
  "blocks": [
    {
      "type": "section",
      "block_id": "category-block",
      "text": { "type": "mrkdwn", "text": "Select a category of the meeting!" },
      "accessory": {
        "type": "static_select",
        "action_id": "category-selection-action",
        "placeholder": { "type": "plain_text", "text": "Select a category" },
        "options": [
          { "text": { "type": "plain_text", "text": "Customer" }, "value": "customer" },
          { "text": { "type": "plain_text", "text": "Partner" }, "value": "partner" },
          { "text": { "type": "plain_text", "text": "Internal" }, "value": "internal" }
        ]
      }
    },
    {
      "type": "input",
      "block_id": "agenda-block",
      "element": { "action_id": "agenda-action", "type": "plain_text_input", "multiline": true },
      "label": { "type": "plain_text", "text": "Detailed Agenda" }
    }
  ]
}
```
**slack-api-client** offers a smooth DSL for building blocks and views. The following code generates **View** objects in a type-safe way.
```java
import com.slack.api.model.view.View;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;
View buildView() {
  return view(view -> view
    .callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .title(viewTitle(title -> title.type("plain_text").text("Meeting Arrangement").emoji(true)))
    .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
    .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
    .privateMetadata("{\"response_url\":\"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\"}")
    .blocks(asBlocks(
      section(section -> section
        .blockId("category-block")
        .text(markdownText("Select a category of the meeting!"))
        .accessory(staticSelect(staticSelect -> staticSelect
          .actionId("category-selection-action")
          .placeholder(plainText("Select a category"))
          .options(asOptions(
            option(plainText("Customer"), "customer"),
            option(plainText("Partner"), "partner"),
            option(plainText("Internal"), "internal")
          ))
        ))
      ),
      input(input -> input
        .blockId("agenda-block")
        .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
        .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
      )
    ))
  );
}
```
If you need to carry some information to the modal, setting `private_metadata` is a good way for it. The `private_metadata` is a single string with a maximum of 3000 characters. So, if you have multiple values, you need to serialize them into a string in a format.
```java
import com.slack.api.bolt.util.JsonOps;
class PrivateMetadata {
  String responseUrl;
  String commandArgument;
}
app.command("/meeting", (req, ctx) -> {
  PrivateMetadata data = new PrivateMetadata();
  data.responseUrl = ctx.getResponseUrl();
  data.commandArgument = req.getPayload().getText();
  return view(view -> view.callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .privateMetadata(JsonOps.toJsonString(data))
    // omitted ...
```
A `trigger_id` is required to open a modal. You can access it in payloads sent by user interactions such as slash command invocations, clicking a button. In Bolt, you can acquire the value by calling `Request.getPayload().getTriggerId()` as it's a part of payloads. More easily, it's also possible to get it by `Context.getTriggerId()`. These methods are defined only when `trigger_id` exists in a payload.
```java
import com.slack.api.methods.response.views.ViewsOpenResponse;
app.command("/meeting", (req, ctx) -> {
  ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView()));
  if (viewsOpenRes.isOk()) return ctx.ack();
  else return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
});
```
The same code in Kotlin looks as below. (New to Kotlin? [Getting Started in Kotlin]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)
```kotlin
app.command("/meeting") { req, ctx ->
  val res = ctx.client().viewsOpen { it
    .triggerId(ctx.triggerId)
    .view(buildView())
  }
  if (res.isOk) ctx.ack()
  else Response.builder().statusCode(500).body(res.error).build()
}
```
In Kotlin, it's much easier to embed multi-line string data in source code. It may be handier to use `viewAsString(String)` method instead.
```kotlin
// Build a view using string interpolation
val commandArg = req.payload.text
val modalView = """
{
  "type": "modal",
  "callback_id": "meeting-arrangement",
  "notify_on_close": true,
  "title": { "type": "plain_text", "text": "Meeting Arrangement" },
  "submit": { "type": "plain_text", "text": "Submit" },
  "close": { "type": "plain_text", "text": "Cancel" },
  "private_metadata": "${commandArg}"
  "blocks": [
    {
      "type": "input",
      "block_id": "agenda-block",
      "element": { "action_id": "agenda-action", "type": "plain_text_input", "multiline": true },
      "label": { "type": "plain_text", "text": "Detailed Agenda" }
    }
  ]
}
""".trimIndent()
val res = ctx.client().viewsOpen { it
  .triggerId(ctx.triggerId)
  .viewAsString(modalView)
}
```
Alternatively, you can use the [Block Kit DSL]({{ site.url | append: site.baseurl }}/guides/composing-messages#block-kit-kotlin-dsl) in conjunction with the Java Builder to construct your view. The Java example above would look like this in Kotlin:
```kotlin
import com.slack.api.model.kotlin_extension.view.blocks
import com.slack.api.model.view.Views.*
fun buildView(): View {
  return view { thisView -> thisView
  .callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .title(viewTitle { it.type("plain_text").text("Meeting Arrangement").emoji(true) })
    .submit(viewSubmit { it.type("plain_text").text("Submit").emoji(true) })
    .close(viewClose { it.type("plain_text").text("Cancel").emoji(true) })
    .privateMetadata("""{"response_url":"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz"}""")
    .blocks {
      // You can leverage Kotlin DSL here
      section {
        blockId("category-block")
        markdownText("Select a category of the meeting!")
        staticSelect {
          actionId("category-selection-action")
          placeholder("Select a category")
          options {
            option {
              description("Customer")
              value("customer")
            }
            option {
              description("Partner")
              value("partner")
            }
            option {
              description("Internal")
              value("internal")
            }
          }
        }
      }
      input {
        blockId("agenda-block")
        plainTextInput {
          actionId("agenda-action")
          multiline(true)
        }
        label("Detailed Agenda", emoji = true)
      }
    }
  }
}
```
### `"block_actions"` requests
Basically it's the same with [Interactive Components]({{ site.url | append: site.baseurl }}/guides/interactive-components) but the only difference is that a payload coming from a modal has `view` and also its `private_metadata`
```java
import com.google.gson.Gson;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.util.json.GsonFactory;
import java.util.Map;
View buildViewByCategory(String categoryId, String privateMetadata) {
  Gson gson = GsonFactory.createSnakeCase();
  Map<String, String> metadata = gson.fromJson(privateMetadata, Map.class);
  metadata.put("categoryId", categoryId);
  String updatedPrivateMetadata = gson.toJson(metadata);
  return view(view -> view
    .callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .title(viewTitle(title -> title.type("plain_text").text("Meeting Arrangement").emoji(true)))
    .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
    .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
    .privateMetadata(updatedPrivateMetadata)
    .blocks(asBlocks(
      section(section -> section.blockId("category-block").text(markdownText("You've selected \"" + categoryId + "\""))),
      input(input -> input
        .blockId("agenda-block")
        .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
        .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
      )
    ))
  );
}
app.blockAction("category-selection-action", (req, ctx) -> {
  String categoryId = req.getPayload().getActions().get(0).getSelectedOption().getValue();
  View currentView = req.getPayload().getView();
  String privateMetadata = currentView.getPrivateMetadata();
  View viewForTheCategory = buildViewByCategory(categoryId, privateMetadata);
  ViewsUpdateResponse viewsUpdateResp = ctx.client().viewsUpdate(r -> r
    .viewId(currentView.getId())
    .hash(currentView.getHash())
    .view(viewForTheCategory)
  );
  return ctx.ack();
});
```
It looks like below in Kotlin.
```kotlin
app.blockAction("category-selection-action") { req, ctx ->
  val categoryId = req.payload.actions[0].selectedOption.value
  val currentView = req.payload.view
  val privateMetadata = currentView.privateMetadata
  val viewForTheCategory = buildViewByCategory(categoryId, privateMetadata)
  val viewsUpdateResp = ctx.client().viewsUpdate { it
    .viewId(currentView.id)
    .hash(currentView.hash)
    .view(viewForTheCategory)
  }
  ctx.ack()
}
```
### `"view_submission"` requests
Bolt does many of the commonly required tasks for you. The steps you need to handle would be:
* Specify the `callback_id` to handle (by either of the exact name or regular expression)
* Do whatever to do such as input validations, storing them in a database, talking to external services
* Call `ack()` as an acknowledgment with either of the followings:
  * Sending an empty body means closing only the modal
  * Sending a body with `response_action` (possible values are `"errors"`, `"update"`, `"push"`, `"clear"`)
```java
import com.slack.api.model.view.ViewState;
import java.util.*;
// when a user clicks "Submit"
app.viewSubmission("meeting-arrangement", (req, ctx) -> {
  String privateMetadata = req.getPayload().getView().getPrivateMetadata();
  Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
  String agenda = stateValues.get("agenda-block").get("agenda-action").getValue();
  Map<String, String> errors = new HashMap<>();
  if (agenda.length() <= 10) {
    errors.put("agenda-block", "Agenda needs to be longer than 10 characters.");
  }
  if (!errors.isEmpty()) {
    return ctx.ack(r -> r.responseAction("errors").errors(errors));
  } else {
    // TODO: may store the stateValues and privateMetadata
    // Responding with an empty body means closing the modal now.
    // If your app has next steps, respond with other response_action and a modal view.
    return ctx.ack();
  }
});
```
It looks like below in Kotlin.
```kotlin
// when a user clicks "Submit"
app.viewSubmission("meeting-arrangement") { req, ctx ->
  val privateMetadata = req.payload.view.privateMetadata
  val stateValues = req.payload.view.state.values
  val agenda = stateValues["agenda-block"]!!["agenda-action"]!!.value
  val errors = mutableMapOf<String, String>()
  if (agenda.length <= 10) {
    errors["agenda-block"] = "Agenda needs to be longer than 10 characters."
  }
  if (errors.isNotEmpty()) {
    ctx.ack { it.responseAction("errors").errors(errors) }
  } else {
    // TODO: may store the stateValues and privateMetadata
    // Responding with an empty body means closing the modal now.
    // If your app has next steps, respond with other response_action and a modal view.
    ctx.ack()
  }
}
```
If you respond with `"response_action": "update"` or `"push"`, `response_action` and `view` are required in the response body.
```java
ctx.ack(r -> r.responseAction("update").view(renewedView));
ctx.ack(r -> r.responseAction("push").view(newViewInStack));
```
It looks like below in Kotlin.
```kotlin
ctx.ack { it.responseAction("update").view(renewedView) }
ctx.ack { it.responseAction("push").view(newViewInStack) }
```
#### Publishing Messages After Modal Submissions
`view_submission` payloads don't have `response_url` by default. However, if you have an `input` block asking users a channel to post a message, payloads may provide `response_urls` (`List<ResponseUrl> responseUrls` in Java).
To enable this, set the block element type as either [`channels_select`](https://api.slack.com/reference/block-kit/block-elements#channel_select) or [`conversations_select`](https://api.slack.com/reference/block-kit/block-elements#conversation_select) and add `"response_url_enabled": true`. Refer to [the API document](https://api.slack.com/surfaces/modals/using#modal_response_url) for details.
Also, if you want to automatically set the channel a user is viewing when opening a modal to`initial_conversation(s)`, turn `default_to_current_conversation` on in [`conversations_select`](https://api.slack.com/reference/block-kit/block-elements#conversation_select) / [`multi_conversations_select`](https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select) elements.
```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;
View modalView = view(v -> v
  .type("modal")
  .callbackId("request-modal")
  .submit(viewSubmit(vs -> vs.type("plain_text").text("Start")))
  .blocks(asBlocks(
    section(s -> s
      .text(plainText("The channel we'll post the result"))
      .accessory(conversationsSelect(conv -> conv
        .actionId("notification_conv_id")
        .responseUrlEnabled(true)
        .defaultToCurrentConversation(true)
      ))
    )
)));
```
### `"view_closed"` requests (only when `notify_on_close` is `true`)
Bolt does many of the commonly required tasks for you. The steps you need to handle would be:
* Specify the `callback_id` to handle (by either of the exact name or regular expression)
* Do whatever to do at the timing
* Call `ack()` as an acknowledgment
```java
// when a user clicks "Cancel"
// "notify_on_close": true is required
app.viewClosed("meeting-arrangement", (req, ctx) -> {
  // Do some cleanup tasks
  return ctx.ack();
});
```
It looks like below in Kotlin.
```kotlin
// when a user clicks "Cancel"
// "notify_on_close": true is required
app.viewClosed("meeting-arrangement") { req, ctx ->
  // Do some cleanup tasks
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
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.app_backend.util.JsonPayloadExtractor;
import com.slack.api.app_backend.util.JsonPayloadTypeDetector;
import com.slack.api.util.json.GsonFactory;
PseudoHttpResponse handle(PseudoHttpRequest request) {
  // 1. Verify requests from Slack
  // https://api.slack.com/docs/verifying-requests-from-slack
  // This needs "X-Slack-Signature" header, "X-Slack-Request-Timestamp" header, and raw request body
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }
  // 2. Parse the request body and check the type, callback_id, action_id
  // payload={URL-encoded JSON} in the request body
  JsonPayloadExtractor payloadExtractor = new JsonPayloadExtractor();
  String payloadString = payloadExtractor.extractIfExists(request.getBodyAsString());
  // The value looks like: { "type": "block_actions", "team": { "id": "T1234567", ...
  JsonPayloadTypeDetector typeDetector = new JsonPayloadTypeDetector();
  String payloadType = typeDetector.detectType(payloadString);

  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType != null && payloadType.equals("view_submission")) {
    ViewSubmissionPayload payload = gson.fromJson(payloadString, ViewSubmissionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      // 3. Extract the form data from view.state.values
      // 4. Do whatever to do such as input validations, storing them in database, talking to external services
      // 5. Respond to the Slack API server with 200 OK as an acknowledgment
    }
  } else if (payloadType != null && payloadType.equals("view_closed")) {
    ViewClosedPayload payload = gson.fromJson(payloadString, ViewClosedPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      // 3. Do whatever to do at the timing
      // 4. Respond to the Slack API server with 200 OK as an acknowledgment
    }
  } else if (payloadType != null && payloadType.equals("block_actions")) {
    BlockActionPayload payload = gson.fromJson(payloadString, BlockActionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      if (payload.getActionId().equals("category-selection-action")) {
        // 3. Modify/push a view via API and/or update the modal to hold the sent data as private_metadata
        // 4. Respond to the Slack API server with 200 OK as an acknowledgment
      }
    }
  } else if (payloadType != null && payloadType.equals("block_suggestion")) {
    BlockSuggestionPayload payload = gson.fromJson(payloadString, BlockSuggestionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      if (payload.getActionId().equals("category-selection-action")) {
        List<Option> options = buildOptions(payload.getValue());
        // Return a successful response having `options` in its body
        return PseudoHttpResponse.builder().body(Map.of("options", options)).status(200).build();
      }
    }
  } else {
    // other patterns
    return PseudoHttpResponse.builder().status(404).build();
  }
}
```