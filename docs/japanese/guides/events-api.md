---
lang: ja
---

# イベント API

[イベント API](/apis/events-api/) は、Slack 内でのアクティビティに反応する Slack アプリを作るための洗練された、簡単な方法です。必要なものは Slack アプリの設定と、セキュアなイベントの送信先だけです。

### Slack アプリの設定

イベント API を有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Event Subscriptions** へ遷移します。この画面でいくつかやることがあります。

* **Enable Events** を Off から On にする
* `https://{あなたのドメイン}/slack/events` を **Request URL** に設定 (ソケットモードの場合、この手順は不要です)
* Bot User Event を設定
  * **Subscribe to bot events** をクリック
  * **Add Bot User Event** ボタンをクリック
  * イベントを選択
* 最下部にある **Save Changes** ボタンをクリック


### Bolt アプリがやること

Bolt アプリがイベントへの応答のためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](/authentication/verifying-requests-from-slack)
1. リクエストボディをパースして `event` の中の `type` が処理対象か確認
1. イベントデータを使った任意の処理
1. 受け取ったことを伝えるために Slack API へ `200 OK` 応答

Bolt アプリは Slack API サーバーからのリクエストに対して 3 秒以内に `ack()` メソッドで応答する必要があります。3 秒以内に応答しなかった場合、Slack API は一定時間経過後にリトライします。

---
## コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt 入門](/java-slack-sdk/guides/getting-started-with-bolt)」を読んでください。

Bolt は Slack アプリに必要な共通処理の多くを巻き取ります。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

* 処理する `event.type` を[イベントデータの Java クラス](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model/sdkLatestVersion/slack-api-model-sdkLatestVersion-javadoc.jar/!/com/slack/api/model/event/Event.html)で指定 ([必要に応じて](/reference/events/message)さらに `event.subtype` も考慮)
* イベントデータを使った任意の処理
* 受け取ったことを伝えるために `ack()`

このリクエストは、ユーザーインタラクションからの直接の呼び出しではないので、ペイロードには `response_url` は含まれていません。また、同じ理由から `ctx.ack()` を使ってチャンネルにメッセージを投稿することもできません。もし、対象のイベントがユーザーインタラクションによるもので、そのユーザーへの返信として投稿したい場合は、イベントのペイロードに含まれている `channel` を使って [**chat.postMessage**](/reference/methods/chat.postmessage) API メソッドや類する API を実行してください。

```java
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.event.ReactionAddedEvent;

app.event(ReactionAddedEvent.class, (payload, ctx) -> {
  ReactionAddedEvent event = payload.getEvent();
  if (event.getReaction().equals("white_check_mark")) {
    ChatPostMessageResponse message = ctx.client().chatPostMessage(r -> r
      .channel(event.getItem().getChannel())
      .threadTs(event.getItem().getTs())
      .text("<@" + event.getUser() + "> ご対応いただき、本当にありがとうございました :two_hearts:"));
    if (!message.isOk()) {
      ctx.logger.error("chat.postMessage failed: {}", message.getError());
    }
  }
  return ctx.ack();
});
```

同じコードを Kotlin で書くと以下のようになります（参考：「[Bolt 入門 > Kotlin での設定](/java-slack-sdk/guides/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
app.event(ReactionAddedEvent::class.java) { payload, ctx ->
  val event = payload.event
  if (event.reaction == "white_check_mark") {
    val message = ctx.client().chatPostMessage {
      it.channel(event.item.channel)
        .threadTs(event.item.ts)
        .text("<@${event.user}> ご対応いただき、本当にありがとうございました :two_hearts:")
    }
    if (!message.isOk) {
      ctx.logger.error("chat.postMessage failed: ${message.error}")
    }
  }
  ctx.ack()
}
```

次は別のサンプル例です。`app.message` リスナーを使って簡単にキーワードにマッチする `message` イベントに対して処理を行うことができます。

```java
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.model.event.MessageEvent;

import java.util.Arrays;
import java.util.regex.Pattern;

String notificationChannelId = "D1234567";

// メッセージがモニタリング対象のキーワードを含むか確認
Pattern sdk = Pattern.compile(".*[(Java SDK)|(Bolt)|(slack\\-java\\-sdk)].*", Pattern.CASE_INSENSITIVE);
app.message(sdk, (payload, ctx) -> {
  MessageEvent event = payload.getEvent();
  String text = event.getText();
  MethodsClient client = ctx.client();

  // 👀 のリアクション絵文字をメッセージにつける
  String channelId = event.getChannel();
  String ts = event.getTs();
  ReactionsAddResponse reaction = client.reactionsAdd(r -> r.channel(channelId).timestamp(ts).name("eyes"));
  if (!reaction.isOk()) {
    ctx.logger.error("reactions.add failed: {}", reaction.getError());
  }

  // SDK の作者に通知メッセージを送る
  ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
  if (permalink.isOk()) {
    ChatPostMessageResponse message = client.chatPostMessage(r -> r
      .channel(notificationChannelId)
      .text("An issue with the Java SDK might be reported:\n" + permalink.getPermalink())
      .unfurlLinks(true));
    if (!message.isOk()) {
      ctx.logger.error("chat.postMessage failed: {}", message.getError());
    }
  } else {
    ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
  }
  return ctx.ack();
});
```

もし固定のキーワードを含むだけのパターンであれば、コードはもっとシンプルです。以下では指定されたキーワードをそのまま一部に含んでいるメッセージにマッチします。

```java
app.message(":wave:", (payload, ctx) -> {
  ctx.say("Hello, <@" + payload.getEvent().getUser() + ">");
  return ctx.ack();
});
```

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、以下の擬似コードを読んでみるとわかりやすいかもしれません。

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.events.*;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Slack からのリクエストを検証
  // /authentication/verifying-requests-from-slack
  // "X-Slack-Signature" header, "X-Slack-Request-Timestamp" ヘッダーとリクエストボディを検証
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }
  // 2. リクエストボディをパースして `event` の中の `type` が処理対象か確認
  // リクエストボディは全体が JSON 形式になっています
  String payloadString = request.getBodyAsString();
  EventTypeExtractor eventTypeExtractor = new EventsDispatcherImpl();
  String eventType = eventTypeExtractor.extractEventType(payloadString);
  if (eventType != null && eventType.equals("message")) {
    Gson gson = GsonFactory.createSnakeCase();
    MessagePayload payload = gson.fromJson(payloadString, MessagePayload.class);
    // 3. イベントデータを使った任意の処理
  } else {
    // その他の不明なパターン
    return PseudoHttpResponse.builder().status(404).build();
  }
  // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
  return PseudoHttpResponse.builder().status(200).build();
}
```
