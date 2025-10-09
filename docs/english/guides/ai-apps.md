---
title: Using AI in apps
lang: en
---

:::info[This feature requires a paid plan]

If you don't have a paid workspace for development, you can join the [Developer Program](https://api.slack.com/developer-program) and provision a sandbox with access to all Slack features for free.

:::

The Agents & AI Apps feature comprises a unique messaging experience for Slack. If you're unfamiliar with using the Agents & AI Apps feature within Slack, you'll want to read the [API documentation on the subject](/ai/). Then come back here to implement them with Bolt!

## Configuring your app to support platform AI features {#configuring-your-app}

1. Within [app settings](https://api.slack.com/apps), enable the **Agents & AI Apps** feature.

2. Within the app settings **OAuth & Permissions** page, add the following scopes: 
  * [`assistant:write`](/reference/scopes/assistant.write)
  * [`chat:write`](/reference/scopes/chat.write)
  * [`im:history`](/reference/scopes/im.history)

3. Within the app settings **Event Subscriptions** page, subscribe to the following events: 
  * [`assistant_thread_started`](/reference/events/assistant_thread_started)
  * [`assistant_thread_context_changed`](/reference/events/assistant_thread_context_changed)
  * [`message.im`](/reference/events/message.im)

## The `Assistant` class instance {#assistant-class}

The [`Assistant`](/tools/java-slack-sdk/reference#the-assistantconfig-configuration-object) class can be used to handle the incoming events expected from a user interacting with an app in Slack that has the Agents & AI Apps feature enabled. A typical flow would look like:

1. [The user starts a thread](#handling-a-new-thread). The `Assistant` class handles the incoming [`assistant_thread_started`](/reference/events/assistant_thread_started) event.
2. [The thread context may change at any point](#handling-thread-context-changes). The `Assistant` class can handle any incoming [`assistant_thread_context_changed`](/reference/events/assistant_thread_context_changed) events. The class also provides a default `context` store to keep track of thread context changes as the user moves through Slack.
3. [The user responds](#handling-user-response). The `Assistant` class handles the incoming [`message.im`](/reference/events/message.im) event. 

```java
App app = new App();
Assistant assistant = new Assistant(app.executorService());

assistant.threadStarted((req, ctx) -> {
  try {
    ctx.say(r -> r.text("Hi, how can I help you today?"));
    ctx.setSuggestedPrompts(Collections.singletonList(
      SuggestedPrompt.create("What does SLACK stand for?")
    ));
  } catch (Exception e) {
    ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
  }
});

assistant.userMessage((req, ctx) -> {
  try {
    ctx.setStatus("is typing...");
    Thread.sleep(500L);
    if (ctx.getThreadContext() != null) {
      String contextChannel = ctx.getThreadContext().getChannelId();
      ctx.say(r -> r.text("I am ware of the channel context: <#" + contextChannel + ">"));
    } else {
      ctx.say(r -> r.text("Here you are!"));
    }
  } catch (Exception e) {
    ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
    try {
      ctx.say(r -> r.text(":warning: Sorry, something went wrong during processing your request!"));
    } catch (Exception ee) {
      ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
    }
  }
});

app.assistant(assistant);
```

While the `assistant_thread_started` and `assistant_thread_context_changed` events do provide Slack-client thread context information, the `message.im` event does not. Any subsequent user message events won't contain thread context data. For that reason, Bolt not only provides a way to store thread context — the `threadContextService` property — but it also provides a `DefaultAssistantThreadContextService` instance that is utilized by default. This implementation relies on storing and retrieving [message metadata](/messaging/message-metadata/) as the user interacts with the app.

If you do provide your own `threadContextService` property, it must feature `get` and `save` methods.

:::tip[Tip]
Be sure to give the [reference docs](/tools/java-slack-sdk/reference#agents--assistants) a look!
:::

## Handling a new thread {#handling-a-new-thread}

When the user opens a new thread with your AI-enabled app, the [`assistant_thread_started`](/reference/events/assistant_thread_started) event will be sent to your app.

:::tip[Tip]
When a user opens a thread with your app while in a channel, the channel info is stored as the thread's `AssistantThreadContext` data. You can grab that info by using the `context.getThreadContext()` utility, as subsequent user message event payloads won't include the channel info.
:::

### Block Kit interactions in the app thread {#block-kit-interactions}

For advanced use cases, Block Kit buttons may be used instead of suggested prompts, as well as the sending of messages with structured [metadata](/messaging/message-metadata/) to trigger subsequent interactions with the user.

For example, an app can display a button like "Summarize the referring channel" in the initial reply. When the user clicks the button and submits detailed information (such as the number of messages, days to check, the purpose of the summary, etc.), the app can handle that information and post a message that describes the request with structured metadata.

By default, apps can't respond to their own bot messages (Bolt prevents infinite loops by default). However, if you set `ignoringSelfAssistantMessageEventsEnabled` to false and add a `botMessage` listener to your `Assistant` middleware, your app can continue processing the request as shown below:

```java
App app = new App(AppConfig.builder()
  .singleTeamBotToken(System.getenv("SLACK_BOT_TOKEN"))
  .ignoringSelfAssistantMessageEventsEnabled(false)
  .build());

Assistant assistant = new Assistant(app.executorService());

assistant.threadStarted((req, ctx) -> {
  try {
    ctx.say(r -> r
      .text("Hi, how can I help you today?")
      .blocks(Arrays.asList(
        section(s -> s.text(plainText("Hi, how I can I help you today?"))),
        actions(a -> a.elements(Collections.singletonList(
          button(b -> b.actionId("assistant-generate-numbers").text(plainText("Generate numbers")))
        )))
      ))
    );
  } catch (Exception e) {
    ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
  }
});

app.blockAction("assistant-generate-numbers", (req, ctx) -> {
  app.executorService().submit(() -> {
    Map<String, Object> eventPayload = new HashMap<>();
    eventPayload.put("num", 20);
    try {
      ctx.client().chatPostMessage(r -> r
        .channel(req.getPayload().getChannel().getId())
        .threadTs(req.getPayload().getMessage().getThreadTs())
        .text("OK, I will generate numbers for you!")
        .metadata(Message.Metadata.builder().eventType("assistant-generate-numbers").eventPayload(eventPayload).build())
      );
    } catch (Exception e) {
      ctx.logger.error("Failed to post a bot message: {e}", e);
    }
  });
  return ctx.ack();
});

assistant.botMessage((req, ctx) -> {
  if (req.getEvent().getMetadata() != null
    && req.getEvent().getMetadata().getEventType().equals("assistant-generate-numbers")) {
  try {
    ctx.setStatus("is typing...");
    Double num = (Double) req.getEvent().getMetadata().getEventPayload().get("num");
    Set<String> numbers = new HashSet<>();
    SecureRandom random = new SecureRandom();
    while (numbers.size() < num) {
      numbers.add(String.valueOf(random.nextInt(100)));
    }
    Thread.sleep(1000L);
    ctx.say(r -> r.text("Her you are: " + String.join(", ", numbers)));
  } catch (Exception e) {
    ctx.logger.error("Failed to handle assistant bot message event: {e}", e);
  }
  }
});

assistant.userMessage((req, ctx) -> {
  try {
    ctx.setStatus("is typing...");
    ctx.say(r -> r.text("Sorry, I couldn't understand your comment."));
  } catch (Exception e) {
    ctx.logger.error("Failed to handle assistant user message event: {e}", e);
    try {
      ctx.say(r -> r.text(":warning: Sorry, something went wrong during processing your request!"));
    } catch (Exception ee) {
      ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
    }
  }
});

app.assistant(assistant);
```

## Handling thread context changes {#handling-thread-context-changes}

When the user switches channels, the [`assistant_thread_context_changed`](/reference/events/assistant_thread_context_changed) event will be sent to your app. 

If you use the built-in `Assistant` middleware without any custom configuration, the updated context data is automatically saved as [message metadata](/messaging/message-metadata/) of the first reply from the assistant bot. 

As long as you use the built-in approach, you don't need to store the context data within a datastore. The downside of this default behavior is the overhead of additional calls to the Slack API. These calls include those to `conversations.history`, which are used to look up the stored message metadata that contains the thread context (via `context.getThreadContextService().findCurrentContext(channelId, threadTs)`).

If you prefer storing this data elsewhere, you can pass your own custom `AssistantThreadContextService` implementation to the `Assistant` constructor. We provide `DefaultAssistantThreadContextService`, which is a reference implementation that uses the app thread message metadata. You can use this for production apps, but if you want to use a different datastore for it, you can implement your own class that inherits `AssistantThreadContextService` interface.

```java
Assistant assistant = new Assistant(new YourOwnAssistantThreadContextService());
```

## Handling the user response {#handling-user-response}

When the user messages your app, the [`message.im`](/reference/events/message.im) event will be sent to your app.

Messages sent to the app do not contain a [subtype](/reference/events/message) and must be deduced based on their shape and any provided [message metadata](/messaging/message-metadata/).

There are three utilities that are particularly useful in curating the user experience:
* [`say`](https://docs.slack.dev/tools/bolt-python/reference/#slack_bolt.Say)
* [`setTitle`](https://docs.slack.dev/tools/bolt-python/reference/#slack_bolt.SetTitle)
* [`setStatus`](https://docs.slack.dev/tools/bolt-python/reference/#slack_bolt.SetStatus)

## Full example: Assistant Simple App {#full-example}

Below is the `AssistantSimpleApp.java` file of the [Assistant Template repo](https://github.com/slackapi/java-slack-sdk/tree/d29a29afff9f2a518495d618502cb7b292e2eb14/bolt-socket-mode/src/test/java/samples) we've created for you to build off of.

```java
package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.Assistant;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.assistant.SuggestedPrompt;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;

import java.util.Collections;

public class AssistantSimpleApp {

    public static void main(String[] args) throws Exception {
        String botToken = System.getenv("SLACK_BOT_TOKEN");
        String appToken = System.getenv("SLACK_APP_TOKEN");

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());

        Assistant assistant = new Assistant(app.executorService());

        assistant.threadStarted((req, ctx) -> {
            try {
                ctx.say("Hi, how can I help you today?");
                ctx.setSuggestedPrompts(r -> r
                        .title("Select one of the following:") // optional
                        .prompts(Collections.singletonList(SuggestedPrompt.create("What does SLACK stand for?")))
                );
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
            }
        });

        assistant.userMessage((req, ctx) -> {
            try {
                // ctx.setStatus(r -> r.status("is typing...")); works too
                ctx.setStatus("is typing...");
                Thread.sleep(500L);
                if (ctx.getThreadContext() != null) {
                    String contextChannel = ctx.getThreadContext().getChannelId();
                    ctx.say("I am ware of the channel context: <#" + contextChannel + ">");
                } else {
                    ctx.say("Here you are!");
                }
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                try {
                    ctx.say(":warning: Sorry, something went wrong during processing your request!");
                } catch (Exception ee) {
                    ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                }
            }
        });

        assistant.userMessageWithFiles((req, ctx) -> {
            try {
                ctx.setStatus("is analyzing the files...");
                Thread.sleep(500L);
                ctx.setStatus("is still checking the files...");
                Thread.sleep(500L);
                ctx.say("Your files do not have any issues!");
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                try {
                    ctx.say(":warning: Sorry, something went wrong during processing your request!");
                } catch (Exception ee) {
                    ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                }
            }
        });

        app.use(assistant);

        app.event(MessageEvent.class, (req, ctx) -> {
            return ctx.ack();
        });

        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("You can help you at our 1:1 DM!");
            return ctx.ack();
        });

        new SocketModeApp(appToken, app).start();
    }
}
```
