---
layout: ja
title: "モーダル"
lang: ja
---

# モーダル

[モーダル](https://api.slack.com/surfaces/modals/using)は、ユーザからデータを収集したり、動的でインタラクティブな表示を見せることに特化したインターフェースです。モーダルは Slack 内でユーザが手短でありながらも深いアプリとのインタラクションを行うことができるインターフェースです。モーダルは [Block Kit](https://api.slack.com/block-kit) の視覚的でインタラクティブなコンポーネントを使って構成されます。

### Slack アプリの設定

モーダルを使うための最初のステップは、[インタラクティブコンポーネント]({{ site.url | append: site.baseurl }}/guides/ja/interactive-components)を有効にすることです。[Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Interactive Components** へ遷移します。このページで以下の設定を行います。

* **Interactivity** を Off から On にする
* `https://{あなたのドメイン}/slack/events` を **Request URL** に設定
* 最下部にある **Save Changes** ボタンをクリック

### Bolt アプリがやること

モーダルのハンドリングには 3 つのパターンがあります。いつものように、Bolt アプリは Slack API サーバーからのリクエストに対して 3 秒以内に `ack()` メソッドで応答する必要があります。3 秒以内に応答しなかった場合、コマンドを実行したユーザーに対して Slack 上でタイムアウトした旨が通知されます。

#### `block_actions` リクエスト

ユーザーがモーダル内の[インタラクティブなコンポーネント](https://api.slack.com/reference/block-kit/interactive-components)を使用して何かアクションを起こしたとき、アプリは [`"block_actions"` という type のペイロード]((https://api.slack.com/reference/interaction-payloads/block-actions))を受信します。このリクエストを処理するためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `action_id` が処理対象か確認
1. [views.* API](https://api.slack.com/methods/views.update) を使って[書き換えたり、上に新しく追加したり](https://api.slack.com/surfaces/modals/using#modifying)する and/or 必要に応じて送信された情報を [private_metadata](https://api.slack.com/surfaces/modals/using#carrying_data_between_views) に保持
1. 受け取ったことを伝えるために Slack API へ 200 OK 応答

#### `view_submission` リクエスト

ユーザーがモーダル最下部の Submit ボタンを押してフォームの送信を行ったとき、[`"view_submission"` という type のペイロード](https://api.slack.com/reference/interaction-payloads/views#view_submission)を受信します。このリクエストを処理するためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `type` が `view_submission` かつ `callback_id` が処理対象かを確認
1. `view.state.values` からフォーム送信された情報を抽出
1. 入力バリデーション、データベースへの保存、外部サービスとの連携など任意の処理
1. 以下のいずれかによって受け取ったことを伝えるために Slack API へ 200 OK 応答:
  * 空のボディで応答してモーダルを閉じる
  * `response_action` (可能な値は `errors`, `update`, `push`, `clear`) を指定して応答する

#### `view_closed` リクエスト (`notify_on_close` が `true` のときのみ)

ユーザーがモーダルの Cancel ボタンや x ボタンを押したとき、[`"view_closed"` という type のペイロード](https://api.slack.com/reference/interaction-payloads/views#view_closed) を受信する場合があります。これらのボタンは blocks ではなく、標準で配置されているものです。このイベントを受信するためには [views.open](https://api.slack.com/methods/views.open) や [views.push](https://api.slack.com/methods/views.push) の API メソッドでモーダルを生成したときに `notify_on_close` を `true` に設定しておく必要があります。このリクエストを処理するためにやらなければならないことは以下の通りです。

1. Slack API からのリクエストを[検証](https://api.slack.com/docs/verifying-requests-from-slack)
1. リクエストボディをパースして `type` が `view_closed` かつ `callback_id` が処理対象かを確認
1. 任意のこのタイミングでやるべきこと
1. 受け取ったことを伝えるために Slack API へ 200 OK 応答

### モーダル開発 Tips

一般に Slack のモーダルを使って開発する上で知っておくべきことがいくつかあります。

* モーダルを開始するには、ユーザーインタラクションのペイロードに含まれる `trigger_id` が必要です
* `"type": "input"` のブロックに含まれる入力項目だけが `view_submission` の `view.state.values` に含まれます
* `"section"`, `"actions"` 等の `"input"` の type ではないブロックでの入力・セレクトメニュー選択は `block_actions` として個別に送信されます
* モーダルを特定するには `callback_id` を使用し、`view.states` 内で入力値を特定するには `block_id` と `action_id` のペアを使用します
* モーダルの内部状態や `block_actions` での入力結果は `view.private_metadata` に保持することができます
* `view_submission` のリクエストは、その応答 (= `ack()`) で `response_action` を指定することでモーダルの次の状態を指示します
* [views.update](https://api.slack.com/methods/views.update)、[views.push](https://api.slack.com/methods/views.push) API メソッドはモーダル内での `block_actions` リクエストを受信したときに使用するものであり、`view_submission` 時にモーダルを操作するための API ではありません

## コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt ことはじめ]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt)」を読んでください。

まずはモーダルを新しく開くところから始めましょう。ここでは以下のようなモーダルを開いてみることにします。

```javascript
{
  "type": "modal",
  "callback_id": "meeting-arrangement",
  "notify_on_close": true,
  "title": { "type": "plain_text", "text": "Meeting Arrangement" },
  "submit": { "type": "plain_text", "text": "Submit" },
  "close": { "type": "plain_text", "text": "Cancel" },
  "private_metadata": "{\"response_url\":\"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\"}",
  "blocks": [
    {
      "type": "section",
      "block_id": "category-block",
      "text": { "type": "mrkdwn", "text": "Select a category of the meeting!" },
      "accessory": {
        "type": "static_select",
        "action_id": "category-selection-action",
        "placeholder": { "type": "plain_text", "text": "Select a category" },
        "options": [
          { "text": { "type": "plain_text", "text": "Customer" }, "value": "customer" },
          { "text": { "type": "plain_text", "text": "Partner" }, "value": "partner" },
          { "text": { "type": "plain_text", "text": "Internal" }, "value": "internal" }
        ]
      }
    },
    {
      "type": "input",
      "block_id": "agenda-block",
      "element": { "action_id": "agenda-action", "type": "plain_text_input", "multiline": true },
      "label": { "type": "plain_text", "text": "Detailed Agenda" }
    }
  ]
}
```

**slack-api-client** は　blocks や views を構築するための扱いやすい SDL を提供しています。以下のコード例は型安全に **View** オブジェクトを生成している例です。

```java
import com.slack.api.model.view.View;
import static com.slack.api.model.view.Views.*;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

View buildView() {
  return view(view -> view
    .callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .title(viewTitle(title -> title.type("plain_text").text("Meeting Arrangement").emoji(true)))
    .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
    .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
    .blocks(asBlocks(
      input(input -> input
        .blockId("agenda-block")
        .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
        .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
      )
    ))
  );
}
```

もし何らかの情報を新しく開くモーダルに引継ぎたい場合、`private_metadata` を利用するとよいでしょう。`private_metadata` は、最大 3,000 文字まで保持できる単一の文字列データです。複数の値を保持したいなら何らかのフォーマットで文字列にシリアライズした上で渡します。

```java
import com.slack.api.bolt.util.JsonOps;

class PrivateMetadata {
  String responseUrl;
  String commandArgument;
}

app.command("/meeting", (req, ctx) -> {
  PrivateMetadata data = new PrivateMetadata();
  data.responseUrl = ctx.getResponseUrl();
  data.commandArgument = req.getPayload().getText();

  return view(view -> view.callbackId("meeting-arrangement")
    .type("modal")
    .notifyOnClose(true)
    .privateMetadata(JsonOps.toJsonString(data))
    // omitted ...
```

モーダルを開くには `trigger_id` が必要です。スラッシュコマンド、ボタンのクリックなどのユーザーインタラクションによって送信されたペイロードの中に含まれています。Bolt では `Request.getPayload().getTriggerId()` のメソッド呼び出しで値を入手できます。もっと簡単にやるなら `Context.getTriggerId()` からの取得できるようになっています。これらのメソッドは `trigger_id` が存在しうるペイロードのときのみ定義されています。

```java
import com.slack.api.methods.response.views.ViewsOpenResponse;

app.command("/meeting", (req, ctx) -> {
  ViewsOpenResponse viewsOpenRes = ctx.client().viewsOpen(r -> r
    .triggerId(ctx.getTriggerId())
    .view(buildView()));
  if (viewsOpenRes.isOk()) return ctx.ack();
  else return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
});
```

Kotlin で書いた同じコードは以下のようになります（参考：「[Bolt ことはじめ > Koltin での設定]({{ site.url | append: site.baseurl }}/guides/ja/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
app.command("/meeting") { req, ctx ->
  val res = ctx.client().viewsOpen { it
    .triggerId(ctx.triggerId)
    .view(buildView())
  }
  if (res.isOk) ctx.ack()
  else Response.builder().statusCode(500).body(res.error).build()
}
```

Kotlin では、複数行に渡る文字列もソースコードに簡単に埋め込むことができます。`viewAsString(String)` メソッドを使う方が手軽なケースもあるでしょう。

```kotlin
// 文字列補間 (string interpolation) を使ってモーダルを構成
val commandArg = req.payload.text
val modalView = """
{
  "type": "modal",
  "callback_id": "meeting-arrangement",
  "notify_on_close": true,
  "title": { "type": "plain_text", "text": "Meeting Arrangement" },
  "submit": { "type": "plain_text", "text": "Submit" },
  "close": { "type": "plain_text", "text": "Cancel" },
  "private_metadata": "${commaondArg}"
  "blocks": [
    {
      "type": "input",
      "block_id": "agenda-block",
      "element": { "action_id": "agenda-action", "type": "plain_text_input", "multiline": true },
      "label": { "type": "plain_text", "text": "Detailed Agenda" }
    }
  ]
}
""".trimIndent()

val res = ctx.client().viewsOpen { it
  .triggerId(ctx.triggerId)
  .viewAsString(modalView)
}
```

### `block_actions` リクエスト

基本的には [インタラクティブコンポーネントのページ]({{ site.url | append: site.baseurl }}/guides/ja/interactive-components) で紹介したものと同じですが、違いとしてはそのペイロードに `view` としてモーダルの内容とその `private_metadata` が含まれていることが挙げられます。

```java
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import java.util.Map;

View buildViewByCategory(String categoryId, String privateMetadata) {
  return null; // TODO
}

app.blockAction("category-selection-action", (req, ctx) -> {
  View currentView = req.getPayload().getView();
  String privateMetadata = currentView.getPrivateMetadata();
  Map<String, Map<String, ViewState.Value>> stateValues = currentView.getState().getValues();
  String categoryId = stateValues.get("category-block").get("category-selection-action").getSelectedOption().getValue();
  View viewForTheCategory = buildViewByCategory(categoryId, privateMetadata);
  ViewsUpdateResponse viewsUpdateResp = ctx.client().viewsUpdate(r -> r
    .viewId(currentView.getId())
    .hash(currentView.getHash())
    .view(viewForTheCategory)
  );
  return ctx.ack();
});
```

Kotlin で書くとこのようになります。

```kotlin
app.blockAction("category-selection-action") { req, ctx ->
  val currentView = req.payload.view
  val privateMetadata = currentView.privateMetadata
  val stateValues = currentView.state.values
  val categoryId = stateValues["category-block"]!!["category-selection-action"]!!.selectedOption.value
  val viewForTheCategory = buildViewByCategory(categoryId, privateMetadata)
  val viewsUpdateResp = ctx.client().viewsUpdate { it
    .viewId(currentView.id)
    .hash(currentView.hash)
    .view(viewForTheCategory)
  }
  ctx.ack()
}
```

### `view_submission` リクエスト

Bolt は Slack アプリに共通で必要となる多くをやってくれます。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

* 処理する `callback_id` 名を指定 (文字列または正規表現)
* 入力バリデーション、データベースへの保存、外部サービスとの連携など任意の処理
* 以下のいずれかのボディとともに受け取ったことを伝えるために `ack()`
  * 空のボディで応答してモーダルを閉じる
  * `response_action` (可能な値は `errors`, `update`, `push`, `clear`) を指定して応答する

```java
import com.slack.api.model.view.ViewState;
import java.util.*;

// ユーザーが "Submit" ボタンをクリックしたとき
app.viewSubmission("meeting-arrangement", (req, ctx) -> {
  String privateMetadata = req.getPayload().getView().getPrivateMetadata();
  Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
  String agenda = stateValues.get("agenda-block").get("agenda-action").getValue();
  Map<String, String> errors = new HashMap<>();
  if (agenda.length() <= 10) {
    errors.put("agenda-block", "Agenda needs to be longer than 10 characters.");
  }
  if (!errors.isEmpty()) {
    return ctx.ack(r -> r.responseAction("errors").errors(errors));
  } else {
    // TODO: ここで stateValues や privateMetadata を保存したりする

    // 空のボディで応答すると、このモーダルは閉じられる
    // モーダルを書き換えて次のステップを見せる場合は response_action と新しいモーダルの view を応答する
    return ctx.ack();
  }
}
```

Kotlin で書くとこのようになります。

```kotlin
// ユーザーが "Submit" ボタンをクリックしたとき
app.viewSubmission("meeting-arrangement") { req, ctx ->
  val privateMetadata = req.payload.view.privateMetadata
  val stateValues = req.payload.view.state.values
  val agenda = stateValues["agenda-block"]!!["agenda-action"]!!.value
  val errors = mutableMapOf<String, String>()
  if (agenda.length <= 10) {
    errors["agenda-block"] = "Agenda needs to be longer than 10 characters."
  }
  if (errors.isNotEmpty()) {
    ctx.ack { it.responseAction("errors").errors(errors) }
  } else {
    // TODO: ここで stateValues や privateMetadata を保存したりする

    // 空のボディで応答すると、このモーダルは閉じられる
    // モーダルを書き換えて次のステップを見せる場合は response_action と新しいモーダルの view を応答する
    ctx.ack() 
  }
}
```

`"response_action": "update"` または `"push"` で応答するとき、`response_action` と `view` はレスポンスボディの必須項目です。

```java
ctx.ack(r -> r.responseAction("update").view(renewedView));
ctx.ack(r -> r.responseAction("push").view(newViewInStack));
```

専用のメソッドも用意されています。

```java
ctx.ackWithUpdate(renewedView);
ctx.ackWithPush(newViewInStack);
```

Kotlin だとこのようになります。

```kotlin
ctx.ack { it.responseAction("update").view(renewedView) }
ctx.ack { it.responseAction("push").view(newViewInStack) }

ctx.ackWithUpdate(renewedView)
ctx.ackWithPush(newViewInStack)
```

### `view_closed` リクエスト (`notify_on_close` が `true` のときのみ)

Bolt は Slack アプリに共通で必要となる多くをやってくれます。それを除いて、あなたのアプリがやらなければならない手順は以下の通りです。

1. 処理する `callback_id` 名を指定 (文字列または正規表現)
1. 任意のこのタイミングでやるべきこと
1. 受け取ったことを伝えるために `ack()`

```java
// モーダルオープン時に "notify_on_close": true である前提でユーザーが "Cancel" ボタンをクリックしたとき
app.viewClosed("meeting-arrangement", (req, ctx) -> {
  // 何らかのクリーンアップ処理
  return ctx.ack();
});
```

Kotlin だとこのようになります。

```kotlin
// モーダルオープン時に "notify_on_close": true である前提でユーザーが "Cancel" ボタンをクリックしたとき
app.viewClosed("meeting-arrangement") { req, ctx ->
  // 何らかのクリーンアップ処理
  ctx.ack()
}
```

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、以下の擬似コードを読んでみるとわかりやすいかもしれません。

```java
import java.util.Map;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.util.json.GsonFactory;

PseudoHttpResponse handle(PseudoHttpRequest request) {

  // 1. Slack API からのリクエストを検証
  // https://api.slack.com/docs/verifying-requests-from-slack
  // "X-Slack-Signature" header, "X-Slack-Request-Timestamp" ヘッダーとリクエストボディを検証
  if (!PseudoSlackRequestVerifier.isValid(request)) {
    return PseudoHttpResponse.builder().status(401).build();
  }

  // 2. リクエストボディをパースして callback_id, action_id が処理対象か確認
  
  // リクエストボディは payload={URL エンコードされた JSON 文字列} の形式
  String payloadString = PseudoPayloadExtractor.extract(request.getBodyAsString());
  // このような値になります: { "type": "block_actions", "team": { "id": "T1234567", ... 
  String payloadType != null &&  = PseudoActionTypeExtractor.extract(payloadString);
  
  Gson gson = GsonFactory.createSnakeCase();
  if (payloadType != null && payloadType.equals("view_submission")) {
    ViewSubmissionPayload payload = gson.fromJson(payloadString, ViewSubmissionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      // 3. `view.state.values` からフォーム送信された情報を抽出
      // 4. 入力バリデーション、データベースへの保存、外部サービスとの連携など任意の処理
      // 5. 受け取ったことを伝えるために Slack API へ 200 OK 応答
    }
  } else if (payloadType != null && payloadType.equals("view_closed")) {
    ViewClosedPayload payload = gson.fromJson(payloadString, ViewClosedPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      // 3. 任意のこのタイミングでやるべきこと
      // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
    }
  } else if (payloadType != null && payloadType.equals("block_actions")) {
    BlockActionPayload payload = gson.fromJson(payloadString, BlockActionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      if (payload.getActionId().equals("category-selection-action")) {
        // 3. views.* API を使って書き換えたり、上に新しく追加したりする and/or 必要に応じて送信された情報を private_metadata に保持
        // 4. 受け取ったことを伝えるために Slack API へ 200 OK 応答
      }
    }
  } else if (payloadType != null && payloadType.equals("block_suggestion")) {
    BlockSuggestionPayload payload = gson.fromJson(payloadString, BlockSuggestionPayload.class);
    if (payload.getCallbackId().equals("meeting-arrangement")) {
      if (payload.getActionId().equals("category-selection-action")) {
        List<Option> options = buildOptions(payload.getValue());
        // 入力されたキーワードを元に `options` を含む応答を返す
        return PseudoHttpResponse.builder().body(Map.of("options", options)).status(200).build();
      }
    }
  } else {
    // その他の不明なパターン
    return PseudoHttpResponse.builder().status(404).build();
  }
}
```
