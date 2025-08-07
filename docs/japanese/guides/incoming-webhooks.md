# Incoming Webhooks

[Incoming Webhooks](/messaging/sending-messages-using-incoming-webhooks) は他のアプリから Slack にメッセージを投稿するためのとてもシンプルな方法です。Incoming Webhook を作成すると、メッセージとその他のオプションを含む JSON ペイロードを送るための一意な URL が払い出されます。

### Slack アプリ設定

この機能を有効にするためには [Slack アプリ管理画面](http://api.slack.com/apps) にアクセスして、そこから開発しているアプリを選択します。そして、左ペインの **Features** > **Incoming Webhooks** に遷移し **Activate Incoming Webhooks** を On に変更して有効化します。

その次に、そのアプリを開発用ワークスペース（Development Workspace）にインストールします。アプリをインストールする度に新しい Webhook の URL が発行されます。

---
## 使い方

まず、Incoming Webhook URL を使ってどのようにメッセージを送信するかを示す **curl** コマンドの例を見ておきましょう。

```bash
curl -X POST \
  -H 'Content-type: application/json' \
  -d '{"text":":candy: はい、アメちゃん！"}' \
  https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
```

これと同様に **Slack SDK for Java** でも Incoming Webhooks でのメッセージ送信は、シンプルかつ手軽です。ペイロードを送信する方法には二通りのやり方があります。

### 文字列のペイロード

単純なやり方は、一つの文字列としてペイロードを作る方法です。このやり方は **curl** コマンドを実行するのとほぼ同等です。

**注**: トークンでの説明と同様、Webhook URL をソースコードに埋め込むことは推奨しません。環境変数やその他の安全な方式で保持するようにしてください。

```java
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;

Slack slack = Slack.getInstance();

String webhookUrl = System.getenv("SLACK_WEBHOOK_URL"); // https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
String payload = "{\"text\":\":candy: はい、アメちゃん！\"}";

WebhookResponse response = slack.send(webhookUrl, payload);
System.out.println(response); // WebhookResponse(code=200, message=OK, body=ok)
```

もし URL が不正だったり、すでに無効化されている場合は、以下のようなレスポンスを受け取るでしょう。

```
WebhookResponse(code=404, message=Not Found, body=no_team)
```

いかなる場合でも、レスポンスはその HTTP ステータスコード・メッセージ、成功・エラーコードだけを伝えるシンプルなテキストのレスポンスボディで構成されます。それに加えて **send** メソッドはネットワークや接続の問題が発生した場合には **java.io.IOException** を throw する可能性があります。

### ペイロードオブジェクト

もう一つのやり方はペイロードを表現する Java オブジェクトを生成して渡すやり方です。

```java
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;

Slack slack = Slack.getInstance();

String webhookUrl = System.getenv("SLACK_WEBHOOK_URL"); // https://hooks.slack.com/services/T1234567/AAAAAAAA/ZZZZZZ
Payload payload = Payload.builder().text("Hello, World!").build();

WebhookResponse response = slack.send(webhookUrl, payload);
System.out.println(response); // WebhookResponse(code=200, message=OK, body=ok)
```

これをもう少し簡潔にするために **WebhookPayloads.payload(function)** というメソッドを使うと便利かもしれません。

```java
import static com.slack.api.webhook.WebhookPayloads.*;

WebhookResponse response = slack.send(webhookUrl, payload(p -> p.text(":candy: はい、アメちゃん！")));
```

「[メッセージの組み立て方](/tools/java-slack-sdk/guides/composing-messages)」で見たように static メソッドを使うと blocks の構成に非常に便利です。

```java
import static com.slack.api.webhook.WebhookPayloads.*;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

WebhookResponse response = slack.send(webhookUrl, payload(p -> p
  .text("Slack couldn't properly display the message.")
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
));
```
