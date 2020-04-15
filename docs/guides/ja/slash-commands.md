---
layout: ja
title: "スラッシュコマンド"
lang: ja
---

# スラッシュコマンド

[スラッシュコマンド](https://api.slack.com/interactivity/slash-commands) は、メッセージ投稿フォームからアプリの機能を呼び出すことができる機能です。

スラッシュコマンドの実行への応答は、とてもよくあるユースケースです。Bolt アプリは Slack API サーバーからのリクエストに対して 3 秒以内に `ack()` メソッドで応答する必要があります。3 秒以内に応答しなかった場合、コマンドを実行したユーザーに対して Slack 上でタイムアウトした旨が通知されます。

### Slack アプリの設定

スラッシュコマンドを有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Slash Commands** へ遷移します。このページで以下の設定を行います。

* **Create New Command** ボタンをクリック
* ダイアログ内で必要なコマンドの情報を入力
  * **Command**: `/hello`
  * **Request URL**: `https://{あなたのドメイン}/slack/events` (ngrok だと `https://{ランダム}.ngrok.io/slack/events`)
  * **Short Description**: お好きな内容で
* **Save** ボタンをクリック

### Bolt アプリがやること

Bolt アプリがスラッシュコマンドの実行を処理するためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `command` が処理対象か確認
1. 返信メッセージを組み立てるなどメインの処理を実行
1. 受け取ったことを伝えるために Slack API へ 200 OK 応答

レスポンスのボディが空の場合、その応答はリクエストを認知したことの通知として認識されます。コマンドを実行したチャンネルにメッセージは何も投稿されません。

## コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt 入門]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt)」を読んでください。

Bolt は Slack アプリに必要な共通処理の多くを巻き取ります。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

* 処理する `command` 名を指定 (そのコマンドの名前、または正規表現)
* メッセージを組み立てるなどメインの処理の実装
* 受け取ったことを伝えるために `ack()`

このペイロードは `response_url` を持っており、例えば `ack()` した後、しばらく経ってからでも返信することができます。URL は発行されてから 30 分間を期限に最大 5 回まで使用することができます。処理が終わったタイミングで `response_url` を使って返信する場合は `ctx.ack()` は引数なしで実行し `ctx.respond()` でメッセージを投稿する、というやり方になります。

以下は、Bolt アプリでのスラッシュコマンドへの応答を示す小さなコード例です。

```java
app.command("/echo", (req, ctx) -> {
  String commandArgText = req.getPayload().getText();
  String channelId = req.getPayload().getChannelId();
  String channelName = req.getPayload().getChannelName();
  String text = "<#" + channelId + "|" + channelName + "> で " + commandArgText + " と言いましたね？ :eyes:";
  return ctx.ack(text); // 200 OK で応答 & メッセージ投稿
});
```

以下は `response_url` を使ってメッセージを投稿している例です。`ctx.respond` は ack した後に非同期で実行することもできます。

```java
app.command("/echo", (req, ctx) -> {
  String text = buildMessage(req);
  ctx.respond(text); // response_url を使って HTTP リクエスト
  return ctx.ack(); // 200 OK で応答
});
```

同じコードを Kotlin で書くと以下のようになります（参考：「[Bolt 入門 > Kotlin での設定]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
app.command("/echo") { req, ctx ->
  val commandArgText = req.payload.text
  val channelId = req.payload.channelId
  val channelName = req.payload.channelName
  val text = "<#${channelId}|${channelName}> で ${commandArgText} と言いましたね？ :eyes:"
  ctx.ack(text)
}

app.command("/echo") { req, ctx ->
  val text = buildMessage(req)
  ctx.respond(text) // response_url を使って HTTP リクエスト
  ctx.ack()
}
```

この SDK で [Block Kit](https://api.slack.com/block-kit) を使ったメッセージを組み立てる方法は「[メッセージの組み立て方]({{ site.url | append: site.baseurl }}/guides/ja/composing-messages)」を参考にしてください。

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、以下の擬似コードを読んでみるとわかりやすいかもしれません。

```java
import java.util.Map;
import com.slack.api.Slack;
import com.slack.api.app_backend.slash_commands.*;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Slack からのリクエストを検証
  // https://api.slack.com/docs/verifying-requests-from-slack
  // "X-Slack-Signature" header, "X-Slack-Request-Timestamp" ヘッダーとリクエストボディを検証
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. リクエストボディをパースして `command` が処理対象か確認
  SlashCommandPayloadParser parser = new SlashCommandPayloadParser();

  // リクエストボディはこんな感じになっています:
  //   token=gIkuvaNzQIHg97ATvDxqgjtO&team_id=T0001&team_domain=example
  //   &enterprise_id=E0001&enterprise_name=Globular%20Construct%20Inc
  //   &channel_id=C2147483705&channel_name=test
  //   &user_id=U2147483697&user_name=Steve
  //   &command=weather&text=94070&response_url=https://hooks.slack.com/commands/1234/5678
  //   &trigger_id=123.123.123
  String requestBody = request.getBodyAsString();

  SlashCommandPayload payload = parser.parse(requestBody);

  if (payload.getCommand().equals("/echo")) {
    // 3. 返信メッセージを組み立てるなどメインの処理を実行
    String commandArgText = payload.getText();
    String channelId = payload.getChannelId();
    String channelName = payload.getChannelName();
    String text = "<#" + channelId + "|" + channelName + "> で " + commandArgText + " と言いましたね？ :eyes:";

    // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
    return PseudoHttpResponse.builder()
      .status(200)
      .body(PseudoJsonOps.serialize(Map.of("text", text))) // 返信メッセージを含める
      .build();
  } else {
    return PseudoHttpResponse.builder().status(404).build();
  }
}
```

Bolt の内部では `ctx.respond()` による `response_url` を使ったメッセージ送信を以下のようなコードで実現しています。

```java
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.webhook.WebhookResponse;

// response_url を使って返信
Slack slack = Slack.getInstance();
SlashCommandResponseSender responder = new SlashCommandResponseSender(slack);
SlashCommandResponse reply = SlashCommandResponse.builder().text(text).build();
WebhookResponse result = responder.send(payload.getResponseUrl(), reply);
```
