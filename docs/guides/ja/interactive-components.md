---
layout: ja
title: "インタラクティブコンポーネント"
lang: ja
---

# インタラクティブコンポーネント

[インタラクティブコンポーネント](https://api.slack.com/reference/block-kit/interactive-components)は、様々な[サーフェスエリア](https://api.slack.com/surfaces)にインタラクティビティをもたらす [Block Kit](https://api.slack.com/block-kit) エレメントのサブセットです。blocks でのインタラクションはチャンネル内のメッセージ上だけではなく、[モーダル]({{ site.url | append: site.baseurl }}/guides/ja/modals) や [Home タブ]({{ site.url | append: site.baseurl }}/guides/ja/app-home) でも発生します。

この SDK で [Block Kit](https://api.slack.com/block-kit) を使ったメッセージを組み立てる方法は「[メッセージの組み立て方]({{ site.url | append: site.baseurl }}/guides/ja/composing-messages)」を参考にしてください。

### Slack アプリの設定

インタラクティブコンポーネントを有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Interactivity & Shortcuts** へ遷移します。このページで以下の設定を行います。

* **Interactivity** を Off から On にする
* `https://{あなたのドメイン}/slack/events` を **Request URL** に設定 (ソケットモードの場合、この手順は不要です)
* 最下部にある **Save Changes** ボタンをクリック

### Bolt アプリがやること

Bolt アプリがユーザーインタラクションへの応答のためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `action_id` が処理対象か確認
1. ユーザとの次のインタラクションのためのメッセージやその他のインターフェースを構築
1. 受け取ったことを伝えるために Slack API へ 200 OK 応答

Bolt アプリは Slack API サーバーからのリクエストに対して 3 秒以内に `ack()` メソッドで応答する必要があります。3 秒以内に応答しなかった場合、コマンドを実行したユーザーに対して Slack 上でタイムアウトした旨が通知されます。`external_select` などの場合は `ack()` の引数に正しい形式の内容を含める必要があります。

---
## コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt 入門]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt)」を読んでください。

Bolt は Slack アプリに必要な共通処理の多くを巻き取ります。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

* 処理する `action_id` 名を指定 (文字列または正規表現)
* ユーザとの次のインタラクションのためのメッセージやその他のインターフェースを構築
* 受け取ったことを伝えるために `ack()`

このペイロードは `response_url` を持っており、例えば `ack()` した後、しばらく経ってからでも返信することができます。URL は発行されてから 30 分間を期限に最大 5 回まで使用することができます。処理が終わったタイミングで `response_url` を使って返信する場合は `ctx.ack()` は引数なしで実行し `ctx.respond()` でメッセージを投稿する、というやり方になります。

以下のような、ボタンを一つ含むシンプルな `actions` タイプのブロックがあるとします。

```javascript
{
  "type": "actions",
  "elements": [{
    "type": "button",
    "action_id": "button-action",
    "text": { "type": "plain_text", "text": "Button", "emoji": true },
    "value": "button's value"
  }]
}
```

ユーザがそのボタンをクリックすると `"block_actions"` タイプのリクエストが `"button-action"` という `action_id` とともに Bolt アプリに送信されます。

```java
// ユーザが actions ブロック内のボタンをクリックしたとき
app.blockAction("button-action", (req, ctx) -> {
  String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
  if (req.getPayload().getResponseUrl() != null) {
    // ボタンがメッセージ内であれば、そのメッセージがあるチャンネルにメッセージを返信
    ctx.respond("You've sent " + value + " by clicking the button!");
  }
  return ctx.ack();
});
```

Kotlin でのサンプルコードは以下のようになります（参考：「[Bolt 入門 > Kotlin での設定]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
app.blockAction("button-action") { req, ctx ->
  val value = req.payload.actions[0].value
  if (req.payload.responseUrl != null) {
    ctx.respond("You've sent ${value} by clicking the button!")
  }
  ctx.ack()
}
```

次は[外部データソース（external data source）を使ったセレクトメニュー](https://api.slack.com/reference/block-kit/block-elements#external_select)の例です。

```javascript
{
  "block_id": "topics",
  "type": "section",
  "text": { "type": "mrkdwn", "text": "Select the meeting topics" },
  "accessory": {
    "action_id": "topics-action",
    "type": "multi_external_select",
    "min_query_length": 1,
    "placeholder": { "type": "plain_text", "text": "Select", "emoji": true }
  }
}
```

このようなタイプのセレクトメニューでは、Bolt アプリは `"topics-action"` という `action_id` で `block_suggestion`、`block_actions` タイプのリクエストのハンドリングをすることが必要です。

```java
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.model.block.composition.PlainTextObject;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

final List<Option> allOptions = Arrays.asList(
  new Option(plainText("Schedule", true), "schedule"),
  new Option(plainText("Budget", true), "budget"),
  new Option(plainText("Assignment", true), "assignment")
);

// ユーザーが "Topics" のセレクトメニューで何かキーワードを入力したとき
app.blockSuggestion("topics-action", (req, ctx) -> {
  String keyword = req.getPayload().getValue();
  List<Option> options = allOptions.stream()
    .filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword))
    .collect(toList());
  return ctx.ack(r -> r.options(options.isEmpty() ? allOptions : options));
});

// ユーザーが "Topics" のセレクトメニューでアイテムを選択したとき
app.blockAction("topics-action", (req, ctx) -> {
  return ctx.ack();
});
```

Kotlin で書いた同じコードは以下のようになります（参考：「[Bolt 入門 > Kotlin での設定]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
import com.slack.api.app_backend.interactive_components.response.Option
import com.slack.api.model.block.composition.BlockCompositions.plainText // static import
import com.slack.api.model.block.composition.PlainTextObject

val allOptions = listOf(
  Option(plainText("Schedule", true), "schedule"),
  Option(plainText("Budget", true), "budget"),
  Option(plainText("Assignment", true), "assignment")
)

// ユーザーが "Topics" のセレクトメニューで何かキーワードを入力したとき
app.blockSuggestion("topics-action") { req, ctx ->
  val keyword = req.payload.value
  val options = allOptions.filter { (it.text as PlainTextObject).text.contains(keyword) }
  ctx.ack { it.options(if (options.isEmpty()) allOptions else options) }
}
// ユーザーが "Topics" のセレクトメニューでアイテムを選択したとき
app.blockAction("topics-action") { req, ctx ->
  ctx.ack()
}
```

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、以下の擬似コードを読んでみるとわかりやすいかもしれません。

```java
import java.util.*;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.app_backend.util.JsonPayloadExtractor;
import com.slack.api.app_backend.util.JsonPayloadTypeDetector;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Slack からのリクエストを検証
  // https://api.slack.com/docs/verifying-requests-from-slack
  // "X-Slack-Signature" header, "X-Slack-Request-Timestamp" ヘッダーとリクエストボディを検証
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. リクエストボディをパースして `action_id` が処理対象か確認

  // リクエストボディは payload={URL エンコードされた JSON 文字列} の形式
  JsonPayloadExtractor payloadExtractor = new JsonPayloadExtractor();
  String payloadString = payloadExtractor.extractIfExists(request.getBodyAsString());
  // このような値になります: { "type": "block_actions", "team": { "id": "T1234567", ... 
  JsonPayloadTypeDetector typeDetector = new JsonPayloadTypeDetector();
  String payloadType = typeDetector.detectType(payloadString);
  
  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType != null && payloadType.equals("block_actions")) {
    BlockActionPayload payload = gson.fromJson(payloadString, BlockActionPayload.class);
    if (payload.getActionId().equals("topics-action")) {
      // 3. ユーザとの次のインタラクションのためのメッセージやその他のインターフェースを構築
    }
  } else if (payloadType != null && payloadType.equals("block_suggestion")) {
    BlockSuggestionPayload payload = gson.fromJson(payloadString, BlockSuggestionPayload.class);
    if (payload.getActionId().equals("topics-action")) {
      List<Option> options = buildOptions(payload.getValue());
      // 入力されたキーワードを元に `options` を含む応答を返す
      return PseudoHttpResponse.builder().body(Map.of("options", options)).status(200).build();
    }
  } else {
    // その他の不明なパターン
    return PseudoHttpResponse.builder().status(404).build();
  }
  // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
  return PseudoHttpResponse.builder().status(200).build();
}
```
