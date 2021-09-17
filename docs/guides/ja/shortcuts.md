---
layout: ja
title: "ショートカット"
lang: ja
---


# ショートカット

[ショートカット](https://api.slack.com/interactivity/shortcuts)は、クイックスイッチャーから呼び出すことのできるスラッシュコマンドの進化形です。ユーザーは Slack 内の直感的なサーフェスエリアであなたのアプリのワークフローを起動することができるようになります。

Slack アプリは 3 秒以内に `ack()` メソッドでショッートカット実行のリクエストに対して応答をする必要があります。

---
## グローバル / メッセージショートカット

#### Slack アプリの設定

ショートカットを有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Interactivity & Shortcuts** へ遷移します。このページで以下の設定を行います。

* **Interactivity** を Off から On にする
* `https://{あなたのドメイン}/slack/events` を **Request URL** に設定 (ソケットモードの場合、この手順は不要です)
* **Shortcuts** セクションでショートカットを設定
  * **Name**, **Short Description**, **Callback ID**
* 最下部にある **Save Changes** ボタンをクリック

指定された **Callback ID** は Slack API からのペイロードの中で `callback_id` として送信されます。

#### Bolt アプリがやること

Bolt アプリがショートカットへの応答のためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `callback_id` が処理対象か確認
1. 返信メッセージを組み立てるなどメインの処理を実行
1. 受け取ったことを伝えるために Slack API へ 200 OK 応答

### コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt 入門]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt)」を読んでください。

Bolt は Slack アプリに必要な共通処理の多くを巻き取ります。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

* 処理する `callback_id` を指定 (そのコマンドの名前、または正規表現)
* メッセージを組み立てるなどメインの処理の実装
* 受け取ったことを伝えるために `ack()`

メッセージショートカットのペイロードは `response_url` を持っており、例えば `ack()` した後、しばらく経ってからでも返信することができます。URL は発行されてから 30 分間を期限に最大 5 回まで使用することができます。処理が終わったタイミングで `response_url` を使って返信する場合は `ctx.ack()` は引数なしで実行し `ctx.respond()` でメッセージを投稿する、というやり方になります。グローバルショートカットのペイロードには `response_url` は含まれません。

グローバルショートカットのペイロードは、デフォルトでは `response_url` を持っていません。しかし、モーダルの中にユーザーにチャンネルを入力してもらうための `input` タイプのブロックがある場合は `response_urls` という項目で受け取ることができます。これを利用するためには [`channels_select`](https://api.slack.com/reference/block-kit/block-elements#channel_select) か [`conversations_select`](https://api.slack.com/reference/block-kit/block-elements#conversation_select) の input type の block element を用意し、かつ、その属性に `"response_url_enabled": true` を設定してください。

以下のサンプルは、ショートカットのリクエストに応答する Bolt アプリの実装の例です。

```java
import com.slack.api.model.Message;
import com.slack.api.model.view.View;
import com.slack.api.methods.response.views.ViewsOpenResponse;

// グローバルショートカットの処理
app.globalShortcut("create-task-shortcut-callback-id", (req, ctx) -> {
  // ペイロードを使ってここで何かする
  ViewsOpenResponse viewsOpenResp = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView()));

  return ctx.ack(); // 受け取ったことを伝えるために Slack API へ 200 OK 応答
});

// メッセージショートカット（旧メッセージアクション）の処理
app.messageShortcut("create-task-shortcut-callback-id", (req, ctx) -> {
  String userId = req.getPayload().getUser().getId();
  Message message = req.getPayload().getMessage();
  // そのメッセージを使ってここで何かする

  ViewsOpenResponse viewsOpenResp = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView(message)));
  if (!viewsOpenResp.isOk()) {
    String errorCode = viewsOpenResp.getError();
    ctx.logger.error("Failed to open a modal view for user: {} - error: {}", userId, errorCode);
    ctx.respond(":x: " + errorCode +  "というエラーでモーダルを開ませんでした");
  }

  return ctx.ack(); // 受け取ったことを伝えるために Slack API へ 200 OK 応答
});

View buildView(Message message) { return null; }
View buildView() { return null; }
```

同じコードを Kotlin で書くと以下のようになります（参考：「[Bolt 入門 > Kotlin での設定]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
// グローバルショートカットの処理
app.globalShortcut("create-task-shortcut-callback-id") { req, ctx -> 
  // ペイロードを使ってここで何かする
  val viewsOpenResp = ctx.client().viewsOpen {
    it.triggerId(ctx.triggerId)
      .view(buildView()))
  }

  ctx.ack() // 受け取ったことを伝えるために Slack API へ 200 OK 応答
}

// メッセージショートカット（旧メッセージアクション）の処理
app.messageShortcut("create-task-shortcut-callback-id") { req, ctx ->
  val userId = req.payload.user.id
  val message = req.payload.message
  // そのメッセージを使ってここで何かする

  val viewsOpenResp = ctx.client().viewsOpen {
    it.triggerId(ctx.triggerId)
      .view(buildView(message))
  }
  if (!viewsOpenResp.isOk) {
    val errorCode = viewsOpenResp.error
    ctx.logger.error("Failed to open a modal view for user: ${userId} - error: ${errorCode}")
    ctx.respond(":x: ${errorCode} というエラーでモーダルを開ませんでした")
  }

  ctx.ack() // 受け取ったことを伝えるために Slack API へ 200 OK 応答
}
```

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、以下の擬似コードを読んでみるとわかりやすいかもしれません。

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.MessageShortcutPayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Slack からのリクエストを検証
  // https://api.slack.com/docs/verifying-requests-from-slack
  // "X-Slack-Signature" header, "X-Slack-Request-Timestamp" ヘッダーとリクエストボディを検証
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. リクエストボディをパースして `callback_id` が処理対象か確認

  // リクエストボディは payload={URL エンコードされた JSON 文字列} の形式
  JsonPayloadExtractor payloadExtractor = new JsonPayloadExtractor();
  String payloadString = payloadExtractor.extractIfExists(request.getBodyAsString());
  // このような値になります: { "type": "shortcut", "team": { "id": "T1234567", ... 
  JsonPayloadTypeDetector typeDetector = new JsonPayloadTypeDetector();
  String payloadType = typeDetector.detectType(payloadString);

  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType.equals("shortcut")) {
    GlobalShortcutPayload payload = gson.fromJson(payloadString, GlobalShortcutPayload.class);
    if (payload.getCallbackId().equals("create-task-shortcut-callback-id")) {
      // 3. 返信メッセージを組み立てるなどメインの処理を実行
    }
  } else if (payloadType.equals("message_action")) {
    MessageShortcutPayload payload = gson.fromJson(payloadString, MessageShortcutPayload.class);
    if (payload.getCallbackId().equals("create-task-shortcut-callback-id")) {
      // 3. 返信メッセージを組み立てるなどメインの処理を実行
    }
  } else {
    // その他の不明なパターン
    return PseudoHttpResponse.builder().status(404).build();
  }

  // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
  return PseudoHttpResponse.builder().status(200).build();
}
```
