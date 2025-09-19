# エージェント・アシスタント

このページは、Bolt を使ってエージェント・アシスタントを実装するための方法を紹介します。この機能に関する一般的な情報については、[こちらのドキュメントページ（英語）](/ai/)を参照してください。

## Slack app configuration

この機能を実装するためには、まず[アプリの設定画面](https://api.slack.com/apps)で **Agents & Assistants** 機能を有効にし、**OAuth & Permissions** のページで [`assistant:write`](/reference/scopes/assistant.write)、[`chat:write`](/reference/scopes/chat.write)、[`im:history`](/reference/scopes/im.history) を**ボットの**スコープに追加し、**Event Subscriptions** のページで [`assistant_thread_started`](/reference/events/assistant_thread_started)、[`assistant_thread_context_changed`](/reference/events/assistant_thread_context_changed)、[`message.im`](/reference/events/message.im) イベントを有効にしてください。

また、この機能は Slack の有料プランでのみ利用可能です。もし開発用の有料プランのワークスペースをお持ちでない場合は、[Developer Program](https://api.slack.com/developer-program) に参加し、全ての有料プラン向け機能を利用可能なサンドボックス環境をつくることができます。

## Examples

ユーザーとのアシスタントスレッド内でのやりとりを処理するには、`assistant_thread_started`、`assistant_thread_context_changed`、`message` イベントの `app.event(...)` リスナーを使うことも可能ですが、Bolt はよりシンプルなアプローチを提供しています。`Assistant` インスタンスを作り、それに必要なイベントリスナーを追加し、最後にこのアシスタント設定を `App` インスタンスに渡すだけで良いのです。

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

ユーザーがチャンネルの横でアシスタンスレッドを開いた場合、そのチャンネルの情報はそのスレッドの `AssistantThreadContext` データとして保持され、 `context.getThreadContextService().findCurrentContext(channelId, threadTs)` ユーティリティを使ってアクセスすることができます。

そのユーザーがチャンネルを切り替えた場合、`assistant_thread_context_changed` イベントがあなたのアプリに送信されます。（上記のコード例のように）組み込みの `Assistant` ミドルウェアをカスタム設定なしで利用している場合、この更新されたチャンネル情報は、自動的にこのアシスタントボットからの最初の返信のメッセージメタデータとして保存されます。これは、組み込みの仕組みを使う場合は、このコンテキスト情報を自前で用意したデータストアに保存する必要はないということです。この組み込みの仕組みの唯一の短所は、追加の Slack API 呼び出しによる処理時間のオーバーヘッドです。具体的には `context.getThreadContextService().findCurrentContext(channelId, threadTs)` を実行したときに、この保存されたメッセージメタデータにアクセスするために `conversations.history` API が呼び出されます。

このデータを別の場所に保存したい場合、自前の `AssistantThreadContextService` 実装を `Assistant` のコンストラクターに渡すことができます。

```java
Assistant assistant = new Assistant(new YourOwnAssistantThreadContextService());
```

<details>

<summary>
アシスタントスレッドでの Block Kit インタラクション
</summary>

より高度なユースケースでは、上のようなプロンプト例の提案ではなく Block Kit のボタンなどを使いたいという場合があるかもしれません。そして、後続の処理のために構造化されたメッセージメタデータを含むメッセージを送信したいという場合もあるでしょう。

例えば、アプリが最初の返信で「参照しているチャンネルを要約」のようなボタンを表示し、ユーザーがそれをクリックして、より詳細な情報（例：要約するメッセージ数・日数、要約の目的など）を送信、アプリがそれを構造化されたメータデータに整理した上でリクエスト内容をボットのメッセージとして送信するようなシナリオです。

デフォルトでは、アプリはそのアプリ自身から送信したボットメッセージに応答することはできません（Bolt にはあらかじめ無限ループを防止する制御が入っているため）。`ignoringSelfAssistantMessageEventsEnabled` を false に設定し、`botMessage` リスナーを `Assistant` ミドルウェアに追加すると、上記の例のようなリクエストを伝えるボットメッセージを使って処理を継続することができるようになります。

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

</details>
