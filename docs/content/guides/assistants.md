---
lang: en
---

# Agents & Assistants

This guide focuses on how to implement Agents & Assistants using Bolt. For general information about the feature, please refer to the [API documentation](https://api.slack.com/docs/apps/ai).

## Slack App Configuration

To get started, you'll need to enable the **Agents & Assistants** feature on [the app configuration page](https://api.slack.com/apps). Then, add [`assistant:write`](https://api.slack.com/scopes/assistant:write), [`chat:write`](https://api.slack.com/scopes/chat:write), and [`im:history`](https://api.slack.com/scopes/im:history) to the **bot** scopes on the **OAuth & Permissions** page. Also, make sure to subscribe to [`assistant_thread_started`](https://api.slack.com/events/assistant_thread_started), [`assistant_thread_context_changed`](https://api.slack.com/events/assistant_thread_context_changed), and [`message.im`](https://api.slack.com/events/message.im) events on the **Event Subscriptions** page.

Please note that this feature requires a paid plan. If you don't have a paid workspace for development, you can join the [Developer Program](https://api.slack.com/developer-program) and provision a sandbox with access to all Slack features for free.

## Examples

To handle assistant thread interactions with humans, although you can implement your agents using `app.event(...)` listeners for `assistant_thread_started`, `assistant_thread_context_changed`, and `message` events, Bolt offers a simpler approach. You just need to create an `Assistant` instance, attach the needed event handlers to it, and then add the assistant to your `App` instance.

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

When a user opens an Assistant thread while in a channel, the channel information is stored as the thread's `AssistantThreadContext` data. You can access this information by using the `get_thread_context` utility. The reason Bolt provides this utility is that the most recent thread context information is not included in the subsequent user message event payload data. Therefore, an app must store the context data when it is changed so that the app can refer to the data in message event listeners.

When the user switches channels, the `assistant_thread_context_changed` event will be sent to your app. If you use the built-in `Assistant` middleware without any custom configuration (like the above code snippet does), the updated context data is automatically saved as message metadata of the first reply from the assistant bot.

As long as you use the built-in approach, you don't need to store the context data within a datastore. The downside of this default behavior is the overhead of additional calls to the Slack API. These calls include those to `conversations.history` which are used to look up the stored message metadata that contains the thread context (via `context.getThreadContextService().findCurrentContext(channelId, threadTs)`).

If you prefer storing this data elsewhere, you can pass your own `AssistantThreadContextService` implementation to the `Assistant` instance:

```java
Assistant assistant = new Assistant(new YourOwnAssistantThreadContextService());
```

<details>

<summary>
Block Kit interactions in the assistant thread
</summary>

For advanced use cases, Block Kit buttons may be used instead of suggested prompts, as well as the sending of messages with structured [metadata](https://api.slack.com/metadata) to trigger subsequent interactions with the user.

For example, an app can display a button like "Summarize the referring channel" in the initial reply. When the user clicks the button and submits detailed information (such as the number of messages, days to check, the purpose of the summary, etc.), the app can handle that information and post a message that describes the request with structured metadata.

By default, your app can't respond to its own bot messages (Bolt prevents infinite loops by default). However, if you set `ignoringSelfAssistantMessageEventsEnabled` to false and add a `botMessage` listener to your `Assistant` middleware, your app can continue processing the request as shown below:

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
        .metadata(new Message.Metadata("assistant-generate-numbers", eventPayload))
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

</details>
