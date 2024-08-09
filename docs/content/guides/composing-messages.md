---
lang: en
---

# Composing Messages

This section shows how to build Slack messages using the `slack-api-client` library. If you're not familiar with [`chat.postMessage`](https://api.slack.com/methods/chat.postMessage) API yet, read [the web api basics page](/guides/web-api-basics) before trying the samples here.

Also, before jumping into Java code, we recommend developing an understanding of composing Slack messages. [Read the API documentation](https://api.slack.com/messaging/composing) for more information.

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

---
## Block Kit Kotlin DSL

If you're using Kotlin, the Java Slack SDK also provides Kotlin-style builders which you can use to build your Block Kit structures.

```kotlin
import com.slack.api.Slack
import com.slack.api.model.block.Blocks.*
import com.slack.api.methods.kotlin_extension.request.chat.blocks

val slack = Slack.getInstance()
val token = System.getenv("token")
val response = slack.methods(token).chatPostMessage { req -> req
  .channel("C1234567")
  .blocks {
    section {
      // "text" fields can be constructed via `plainText()` and `markdownText()`
      markdownText("*Please select a restaurant:*")
    }
    divider()
    actions {
      // To align with the JSON structure, you could put the `elements { }` block around the buttons but for brevity it can be omitted
      // The same is true for things such as the section block's "accessory" container
      button {
          // For instances where only `plain_text` is acceptable, the field's name can be filled with `plain_text` inputs
          text("Farmhouse", emoji = true)
          value("v1")
      }
      button {
          text("Kin Khao", emoji = true)
          value("v2")
      }
    }
  }
}
```

### Installation

You can add the Block Kit Kotlin DSL via 2 artifacts:

|artifactId|Description|
|---|---|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model-kotlin-extension/sdkLatestVersion/slack-api-model-kotlin-extension-sdkLatestVersion-javadoc.jar/!/index.html#package)|The **slack-api-model** Kotlin extension, which adds the Kotlin DSL itself as well as the standalone `withBlocks { }` builder and `View.ViewBuilder`'s `.blocks { }` extension function.|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client-kotlin-extension/sdkLatestVersion/slack-api-client-kotlin-extension-sdkLatestVersion-javadoc.jar/!/index.html#package)|The **slack-api-client** Kotlin extension, which adds the `.blocks { }` extension function to `MethodsClient`'s request object builders for seamless use of the DSL with the Java builders such as **ChatPostEphemeralRequestBuilder**, **ChatPostMessageRequestBuilder**, **ChatScheduleMessageRequestBuilder**, and **ChatUpdateRequestBuilder**.|

#### Adding via Gradle

```groovy
dependencies {
  implementation "com.slack.api:slack-api-model-kotlin-extension:sdkLatestVersion"
  implementation "com.slack.api:slack-api-client-kotlin-extension:sdkLatestVersion"
}
```

#### Adding via Gradle Kotlin DSL

```kotlin
dependencies {
  implementation("com.slack.api:slack-api-model-kotlin-extension:sdkLatestVersion")
  implementation("com.slack.api:slack-api-client-kotlin-extension:sdkLatestVersion")
}
```

### Notable examples and features

Below are some code snippets demonstrating how to use this DSL.

#### Standalone `withBlocks { }` builder which comes with the model extension

You can create lists of blocks outside of the **slack-api-client** Kotlin extension functions with the `withBlocks { }` builder.

```kotlin
import com.slack.api.model.kotlin_extension.block.withBlocks

val blocks = withBlocks {
  section {
    plainText("Now this can be passed to anything that requires a list of LayoutBlocks")
  }
}
```

#### Type safe enums for inputs which require specific string inputs

Type-safe enums are available for properties of some block elements which require specific input strings. 

With this, you get the benefit of verifying your inputs are correct at compile time, and you gain access to Kotlin enum features such as being able to iterate over or retrieve all possible values for these inputs. Versions of these inputs which accept strings are also available, if you prefer.

```kotlin
import com.slack.api.model.kotlin_extension.block.element.ButtonStyle
import com.slack.api.model.kotlin_extension.block.element.ConversationType
import com.slack.api.model.kotlin_extension.block.withBlocks

val blocks = withBlocks { 
  section { 
    plainText("Please select the person or group you would like to send a cat GIF to.")

    // "accessory" is provided here, but it can be omitted for brevity
    accessory { 
      conversationsSelect { 
        // Or alternatively, provide strings via `filter("im", "mpim")` if you'd prefer
        filter(ConversationType.IM, ConversationType.MULTIPARTY_IM)
        placeholder("Where should we send the cat?")
        
        confirm { 
          title("Confirm destination")
          plainText("Are you sure you want to send a cat GIF to this person or group?")
          confirm("Yes, send it")
          deny("Don't send it")
          
          style(ButtonStyle.PRIMARY)
        }
      }
    }
  }
}
```

#### Write DSL extension functions for message templating

Because it is a Kotlin DSL, you benefit from Kotlin language features while you are constructing your messages, one of which being able to create extension functions which reproduce common Block Kit structures. This makes your code less repetitive and easier to read. You also benefit from being able to use conditionals and loops as you construct your blocks.

```kotlin
import com.slack.api.model.kotlin_extension.block.ActionsBlockBuilder
import com.slack.api.model.kotlin_extension.block.withBlocks

fun ActionsBlockBuilder.presentOptions(vararg optionNames: String, prompt: String? = null) {
  staticSelect { 
    if (prompt != null) {
      placeholder(prompt)
    }
    
    options {
      for (optionName in optionNames) {
        option {
          plainText(optionName)
          value(optionName.toLowerCase())
        }
      }
    }
  }
}

val blocks = withBlocks { 
  section {
    markdownText("Please select your favorite color.")
  }
  actions {
    presentOptions(
      "Green", "Red", "Blue", "Yellow", "Orange", "Black",
      prompt = "Pick a color..."
    )
  }
}
```
