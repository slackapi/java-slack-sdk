---
layout: default
title: "Composing Messages"
lang: en
---

# Composing Messages

This section shows how to build Slack messages using **slack-api-client** library. If you're not familiar with [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) API yet, read [this page]({{ site.url | append: site.baseurl }}/guides/web-api-basics) before trying the samples here.

Also, before jumping into Java code, we recommend developing an understand of composing Slack messages. [Read the API documentation](https://api.slack.com/messaging/composing) for more information.

---
## Text Formatting

Composing a text message is the simplest way to post a message to Slack conversations.

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

String channelId = "C1234567";
String text = ":wave: Hi from a bot written in Java!";

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_TOKEN");

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel(channelId)
  .text(text)
);
```

As you see, using `text` is fairly simple. The only thing to know is to give a string value with a valid format. Consult [Basic formatting with `mrkdwn`](https://api.slack.com/reference/surfaces/formatting#basics) for understanding the markup language.

---
## Building Blocks for Rich Message Layouts

[Block Kit](https://api.slack.com/block-kit) is a UI framework for Slack apps that offers a balance of control and flexibility when building experiences in messages and other [surfaces](https://api.slack.com/surfaces).

It may not be so easy to compose a large JSON data structure in Java code. So, we offer setter methods like `blocksAsString(String)` that accept a whole `blocks` part as a single string value. Such method is supposed to be used with loaded external file data or outcomes by template engines.

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
  .blocksAsString("[{\"type\": \"divider\"}]")
);
```

This library also offers a type-safe way to build blocks like the one below. Just by having a few static imports, building blocks is much easier, safer, and more readable to everyone.

```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
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
);
```

You can use this way also for building a message for Incoming Webhooks and `response_url` calls.