---
layout: default
title: "Incoming Webhooks"
lang: en
---

# Incoming Webhooks

[Incoming Webhooks](https://api.slack.com/messaging/webhooks) is a simple way to post messages from apps into Slack. Creating an Incoming Webhook gives you a unique URL to which you send a JSON payload with the message and some options.

### Slack App Configuration

To enable this feature, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, go to **Features** > **Incoming Webhooks** on the left pane, and then turn on **Activate Incoming Webhooks**.

Then, install the app to your development workspace. Each time your app is installed, a new Webhook URL will be generated.

## How To Use 

Here is a **curl** command example demonstrating how to send a message via an Incoming Webhooks URL.

```bash
curl -X POST \
  -H 'Content-type: application/json' \
  -d '{"text":"Hello, World!"}' \
  https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
```

With **Slack SDK for Java**, Sending messages via Incoming Webhooks is still simple and handy. There are two ways to send a payload.

### Build a string payload

A primitive way is to build a payload as a single string. This method is nearly the same as running **curl** command. As with tokens, we don't recommend embedding a webhook URL in your source code. Consider using env variables or some more secure ways to store those.

```java
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;

Slack slack = Slack.getInstance();

String webhookUrl = System.getenv("SLACK_WEBHOOK_URL"); // https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
String payload = "{\"text\":\"Hello, World!\"}";

WebhookResponse response = slack.send(webhookUrl, payload);
System.out.println(response); // WebhookResponse(code=200, message=OK, body=ok)
```

If the URL is invalid or no longer available, you'll receive other responses as below.

```
WebhookResponse(code=404, message=Not Found, body=no_team)
```

In any case, the response consists of its HTTP status code/message and a simple plain text body telling an error code. The **send** method may throw a **java.io.IOException** when having connectivity issues.

### Send using a Payload object

Another way is to build a Java object representing a webhook payload.

```java
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;

Slack slack = Slack.getInstance();

String webhookUrl = System.getenv("SLACK_WEBHOOK_URL"); // https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
Payload payload = Payload.builder().text("Hello, World!").build();

WebhookResponse response = slack.send(webhookUrl, payload);
System.out.println(response); // WebhookResponse(code=200, message=OK, body=ok)
```

To make it a bit more concise, using **WebhookPayloads.payload(function)** may look convenient to some.

```java
import static com.slack.api.webhook.WebhookPayloads.*;

WebhookResponse response = slack.send(webhookUrl, payload(p -> p.text("Hello, World!")));
```

As we learned at [Composing Messages]({{ site.url | append: site.baseurl }}/guides/composing-messages), using static methods for building blocks would be greatly useful.

```java
import static com.slack.api.webhook.WebhookPayloads.*;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

WebhookResponse response = slack.send(webhookUrl, payload(p -> p
  .text("Slack couldn't properly display the message.")
  .blocks(asBlocks(
    section(section -> section.text(markdownText("*Please select a restaurant:*"))),
    divider(),
    actions(actions -> actions
      .elements(asElements(
        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Farmhouse"))).value("v1")),
        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Kin Khao"))).value("v2"))
      ))
    )
  ))
));
```
