---
layout: ja
title: "基本的な Web API の利用"
lang: ja
---

# API クライアントの基本

**slack-api-client** は、シンプル、簡単、柔軟に設定可能な Slack API クライアント実装を提供します。

このページにあるサンプルを試す前に、Java プロジェクトを設定しておく必要があります。もしまだでしたら「[API クライアントのセットアップ](/guides/web-api-client-setup)」にある手順に従ってください。

---
## Slack オブジェクトの初期化

このライブラリの全ての機能は **com.slack.api.Slack** クラスに定義されている多様なインスタンスメソッドから始まります。

```java
import com.slack.api.Slack;

Slack slack = Slack.getInstance();
```

サポートされている全ての API クライアントは、このインスタンスが提供する fluent インターフェース（メソッドチェイン）に従うだけで、初期化して利用することができます。これらの API クライアントの設定をカスタマイズするには **SlackConfig** の設定も必要です。このクラスについてはこのページの末尾にあるセクションを参考にしてください。

以下は **Slack** オブジェクトの API クライアントを生成するためのメソッドの一覧です。

|メソッド|戻り値の型|説明|
|-|-|-|
|**Slack#methods(String)**|**com.slack.api.methods.MethodsClient**|[API メソッド](https://api.slack.com/methods)クライアント|
|**Slack#methodsAsync(String)**|**com.slack.api.methods.AsyncMethodsClient**|[Rate Limits](https://api.slack.com/docs/rate-limits) を十分に考慮した [API メソッド](https://api.slack.com/methods)非同期クライアント|
|**Slack#socketMode(String)**|**com.slack.api.socket_mode.SocketModeClient**|[ソケットモード](https://api.slack.com/apis/connections/socket) の WebSocket クライアント|
|**Slack#rtm(String)**|**com.slack.api.rtm.RTMClient**|[Real Time Messaging (RTM) API](https://api.slack.com/rtm) の WebSocket クライアント|
|**Slack#scim(String)**|**com.slack.api.scim.SCIMClient**|[SCIM API](https://api.slack.com/scim) クライアント|
|**Slack#audit(String)**|**com.slack.api.audit.AuditClient**|[Audit Logs API](https://api.slack.com/docs/audit-logs-api) クライアント|
|**Slack#status()**|**com.slack.api.status.v2.StatusClient**|[Slack Status API](https://api.slack.com/docs/slack-status) クライアント|

[Incoming Webhooks](https://api.slack.com/messaging/webhooks) の解説をお探しですか？もちろん、サポートされています！[こちらのガイド](/guides/incoming-webhooks)を参考にしてください。

---
## API メソッドを実行

最も人気のある Slack の API メソッドは [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) という API で、これはチャンネルなどにメッセージを投稿するために使用されます。

[**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) のような API メソッドを実行するには **MethodsClient** をトークンを与えて初期化する必要があります。トークンは `xoxb-` （ボットトークン）や `xoxp-` （ユーザートークン）で始まる文字列です。これらのトークンは Slack アプリがインストールされたそれぞれのワークスペース毎に払い出されるものです。とりあえず始めるにあたっては、[Slack アプリの管理画面](https://api.slack.com/apps) から開発用ワークスペース（Development Workspace）のトークンを簡単に発行することができます。

**注**: トークンをソースコードにハードコードすることは望ましくありません。意図しない流出を防ぐために、環境変数やそれに類するより安全な方法でトークンを保持することを強くおすすめします。

```java
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

// 環境変数を読み込みます
// トークンがボットトークンであれば `xoxb-`、ユーザートークンであれば `xoxp-` で始まっているはずです
String token = System.getenv("SLACK_TOKEN");

// API メソッドのクライアントを上のトークンと共に初期化します
MethodsClient methods = slack.methods(token);

// API リクエスト内容を構成します
ChatPostMessageRequest request = ChatPostMessageRequest.builder()
  .channel("#random") // ここでは簡単に試すためにチャンネル名を指定していますが `C1234567` のような ID を指定する方が望ましいです
  .text(":wave: Hi from a bot written in Java!")
  .build();

// API レスポンスを Java オブジェクトとして受け取ります
ChatPostMessageResponse response = methods.chatPostMessage(request);
```

うまくいっていれば Slack ワークスペースの **#random** チャンネルで以下のようなメッセージが投稿されていることを確認できるはずです。

<img src="/img/web-api-basics-hello-world.png" width="400" />

ここで実際に何が行われたかをよりクリアに理解するために、上の Java コードと等価な **curl** コマンドの例を見てみましょう。Slack の Web API のコンセプトはとても素直なものなので、与えられたパラメーターがどのように実際の HTTP リクエストで送られるかを容易に理解していただけるかと思います。

```bash
curl -XPOST https://slack.com/api/chat.postMessage \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Authorization: Bearer xoxb-123-123-abcabcabc' \
  -d 'channel=%23random&text=%3Awave%3A%20Hi%20from%20a%20bot%20written%20in%20Java%21'
```

これで上記の Java コードが実際に何をしていたかはおわかりいただけたかと思います！

ただ、上のコード例はちょっと冗長だなと感じた方もいるかもしれません。以下のようにしてよりコードをシンプルにすることができます。ただし、あなたのアプリケーションが Java オブジェクトの生成数を抑える必要があるなら、最初の例のように **MethodsClient** を生成してそれを使い回す方がよいでしょう。

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(ChatPostMessageRequest.builder()
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!")
  .build());
```

これで終わりではありません。リクエストの初期化を関数で行うようにすると、同じコードをよりシンプルにすることができます。

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!"));
```

この最後の例が最も手軽で簡潔なコードであることに異論のある方はほとんどいないはずです。これより先のコード例にはこのスタイルで記述していきます。

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_TOKEN");

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("#random")
  .text(":wave: Hi from a bot written in Java!"));
```

そして [Kotlin](https://kotlinlang.org/) を使えば、このコードはより一層簡潔になります。

```kotlin
import com.slack.api.Slack

val slack = Slack.getInstance()
val token = System.getenv("SLACK_TOKEN")

val response = slack.methods(token).chatPostMessage { it
  .channel("#random")
  .text(":wave: Hi from a bot written in Kotlin!") 
}
```

さらに、リッチなメッセージを構築するためのより Kotlin ネイティブなやり方として [Block Kit Kotlin DSL](/guides/composing-messages#block-kit-kotlin-dsl) もチェックしてみてください。

### レスポンスの扱い

Slack Web API のレスポンス形式にまだ馴染みがなければ「[Evaluating responses（英語）](https://api.slack.com/web#responses)」を読んでみてください。すべての Web API レスポンスは JSON オブジェクトを持ち、そのトップレベルに真偽型の `"ok"` というリクエストの成功・失敗を伝えるためのプロパティがあります。

```json
{
  "ok": true,
  "stuff": "This is good"
}
```

```json
{
  "ok": false,
  "error": "something_bad"
}
```

ですので、レスポンスを処理するときは `isOk()` メソッドで返されるこの真偽値を判定して、後続の処理を分岐することになります。

```java
import com.slack.api.model.Message;

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
  .text("Write one, post anywhere"));
if (response.isOk()) {
  Message postedMessage = response.getMessage();
} else {
  String errorCode = response.getError(); // 例: "invalid_auth", "channel_not_found"
}
```

API メソッドを呼び出すとき、様々な理由からエラーが発生することがあります。

1. 成功のレスポンスを受け取っても、そのレスポンスボディの `"ok"` プロパティが **false** で `"error"` プロパティが `channel_not_found` のような値で設定されていることがあります。このエラーコードの意味・理由は [API メソッドのドキュメント](https://api.slack.com/methods)で確認できます。
2. ネットワーク・Slack API サーバーとの接続の問題で **java.io.IOException** が throw されることがあります。
3. Slack API サーバーから 20x 以外の HTTP ステータスで応答された場合に **com.slack.api.methods.SlackApiException** が throw されることがあります。

**1.** のパターンをどう処理するかを理解するには、[このドキュメント（英語）](https://api.slack.com/web#evaluating_responses)を参考にしてください。

**2.** と **3.** のパターンでは **MethodsClient** は 2 種類の例外を throw する可能性があります。この API クライアントを利用するアプリケーション側で、これらの例外を catch して適切に処理する必要があります。

|例外の型|含まれる情報|発生理由|
|-|-|-|
|**java.io.IOException**|標準的な例外の情報（メッセージ文字列、原因となった例外）|この例外は、リクエストのキャンセル、コネクションの問題やタイムアウトによって発生する可能性があります。|
|**com.slack.api.methods.SlackApiException**|生の HTTP レスポンスオブジェクト、文字列のレスポンスボディ、レスポンスボディからデシリアライズされた **SlackApiErrorResponse** オブジェクト|この例外は、Slack API サーバーが 20x 以外の HTTP ステータスで応答したときに発生する可能性があります。|

十分に考慮されたエラーハンドリングの最終形はこのようになります。

```java
import java.io.IOException;
import com.slack.api.methods.SlackApiException;

try {
  ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
    .channel("C1234567")
    .text("Write one, post anywhere"));
  if (response.isOk()) {
    Message postedMessage = response.getMessage();
  } else {
    String errorCode = response.getError(); // 例: "invalid_auth", "channel_not_found"
  }
} catch (SlackApiException requestFailure) {
  // Slack API が 20x 以外の HTTP ステータスで応答した
} catch (IOException connectivityIssue) {
  // 何らかの接続の問題が発生した
}
```

---
## 他の API メソッド

Slack Web API は [180 以上の API メソッド](https://api.slack.com/methods)を提供しています。他の API メソッドを使うやり方もほぼ同じです。**MethodsClient** のメソッドを正しいトークンと必要なパラメータを指定して呼び出します。

この SDK でサポートされている Java メソッドの一覧にアクセスするには [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/methods/MethodsClient.html) を参照するとよいでしょう。

#### サポートされていない API メソッドを実行

何らかの理由で **slack-api-client** がサポートしていないメソッドを実行する必要がある場合は、以下のようにすることができます。

```java
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiTextResponse;

public class AwesomeMethodResponse implements SlackApiTextResponse {
  private boolean ok;
  private String warning;
  private String error;
  private String needed;
  private String provided;
  private String awesomeness;

  // getter/setter は省略
}

Slack slack = Slack.getInstance();

AwesomeMethodResponse response = slack.methods().postFormWithTokenAndParseResponse(
  req -> req.add("user", "U1234567"), // リクエストボディを構成
  "awesome.method", // API メソッドの名前
  System.getenv("SLACK_TOKEN"), // ボットトークンまたはユーザートークン
  AwesomeMethodResponse.class // レスポンスボディをマッピングするためのクラス
);
```

---
## Rate Limits

Slack プラットフォームの機能・API は、期待通りの快適なユーザー体験を提供するために、その [Rate Limits](https://api.slack.com/docs/rate-limits) に依拠しています。この制限は **"per app per workspace"**（Slack アプリ毎、かつ、それがインストールされたワークスペース毎）で適用されます。それぞれの API は、どれくらいの頻度で呼び出せるかを規定する "Tier" が設定されています。**slack-api-client** はこの Tier の完全なサポートを提供しており、**AsyncMethodsClient** （非同期クライアント）はその実行において Rate Limits を考慮します。

非同期クライアントは、可能な限りバーストリクエストを発生させないために、内部にキューの仕組みを持っています。一方、**MethodsClient** （同期クライアント）はそのような考慮はなく、常に即時でリクエストを送信します。幸いにもこれらの同期・非同期クライアントは協調して **MetricsDatastore** 内のメトリクスを更新します。これにより、非同期クライアントは、今どれくらいのトラフックを Slack プラットフォームに送っているかを正確に把握し、残っている呼び出し量を推測することができます。

このデータストアのデフォルトの実装は、JVM のヒープメモリを使用したインメモリデータベースです。デフォルトの **SlackConfig** はこのインメモリ実装を有効にします。これはほとんどのケースで良い具合に動作するでしょう。この設定で問題ないなら、特に追加で何か設定する必要はありません。

```java
import com.slack.api.Slack;
import com.slack.api.SlackConfig;

SlackConfig config = new SlackConfig();
Slack slack = Slack.getInstance(config);
```

**AsyncMethodsClient** はそのメトリクスデータを考慮しながら動作します。アプリケーション内の API クライアントがすでに大量のリクエストを短時間の間に送信していると判断した場合、エラーにならないように API リクエストの実行を遅延させることがあります。

```java
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.util.concurrent.CompletableFuture;

CompletableFuture<ChatPostMessageResponse> postMessageResult = slack.methodsAsync("xoxb-***")
  .chatPostMessage(r -> r.channel("C01ABC123").text("このメッセージはちょっと遅延するかもです"));
```

もしアプリケーションが複数のノードで構成されている場合、そのノード数を指定することもできます。

```java
import com.slack.api.methods.metrics.MemoryMetricsDatastore;

// 3 台構成で運用しているので 3 を指定します（デフォルトは 1）
config.getMethodsConfig().setMetricsDatastore(new MemoryMetricsDatastore(3));
```

メトリクスのデータストアは、Rate Limits に対処するための有用な情報を提供します。メトリクスデータの構造は以下の通りです。

以下の例は **MethodsStats** オブジェクトを JSON 形式で表現したものです。統計情報は **MethodsConfig** の executor 名単位で記録されます。このメトリクスと統計情報は複数ワークスペースの管理に対応しているため、通常は一つの統計情報をアプリ内の全ての API クライアント間で共有します。もし、あなたの一つのサーバーが複数の Slack アプリとして動作するのであれば **MethodsConfig** をその Slack アプリ毎に作って別々に管理することも可能です。

|キー|可能な値|
|-|-|
|Web API executor の名前|任意の文字列 - デフォルトは `DEFAULT_SINGLETON_EXECUTOR`|
|ワークスペースの ID|API レベルでは **team_id** と呼ばれるもの (例: `T1234567`)|
|`all_completed_calls`|API メソッド毎のリクエスト総数|
|`successful_calls`|API メソッド毎の `"ok": true` レスポンスとなったリクエスト数|
|`unsuccessful_calls`|API メソッド毎の `"ok": false` レスポンスとなったリクエスト数|
|`failed_calls`|API メソッド毎の失敗（**SlackApiException** か **IOException** のいずれかが throw されたケース）したリクエスト数|
|`current_queue_size`|API メソッド毎の現在のキューサイズ|
|`last_minute_requests`|API メソッド毎の直近 60 秒間でのリクエスト総数|
|`rateLimitedMethods`|Rate Limits にひっかかったメソッド名と（`retry-after` レスポンスヘッダーによって）Slack API からサジェストされた再リクエストが可能となる時刻の UNIX 時間（ミリ秒）|

```js
{
  "DEFAULT_SINGLETON_EXECUTOR": {
    "T1234567": {
      "all_completed_calls": {
        "chat.postMessage": 120,
        "users.info": 2,
        "conversations.members": 2
      },
      "successful_calls": {
        "chat.postMessage": 110,
        "users.info": 2,
        "conversations.members": 2
      },
      "unsuccessful_calls": {
        "chat.postMessage": 7
      },
      "failed_calls": {
        "chat.postMessage": 3
      },
      "current_queue_size": {
        "chat.postMessage_C01ABC123": 5,
        "users.info": 0
      },
      "last_minute_requests": {
        "chat.postMessage_C01ABC123": 100,
        "chat.postMessage_C03XYZ555": 3,
        "users.info": 2,
        "conversations.members": 2
      },
      "rateLimitedMethods": {
        "chat.postMessage_C01ABC123": 1582183395064
      }
    }   
  }
}
```

もしこのメトリクスの管理が全く必要ないという場合は、以下の方法で無効化することもできます。

```java
SlackConfig config = new SlackConfig();
config.setStatsEnabled(false);
Slack slack = Slack.getInstance(config);
```

もう少し細かい単位で制御したい場合は、API クライアント単位で同様のフラグを設定できます。

```java
SlackConfig config = new SlackConfig();
// Web API メソッドに対してのみメトリクスの機能を無効化
config.getMethodsConfig().setStatsEnabled(false);
// SCIM や Audit Logs の API では、引き続きメトリクス昨日は有効
Slack slack = Slack.getInstance(config);
```

### Redis によるメトリクスデータストア

統合された一つのデータストアにすべてのノードのメトリクスを集約したい場合は Redis クラスターを利用することをおすすめします。API クライアント側の設定は非常に簡単です。以下は同じホストで稼働する Redis サーバーを利用する例です。

```java
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.RedisMetricsDatastore;
import redis.clients.jedis.JedisPool;

SlackConfig config = new SlackConfig();
// macOS では、以下の手順で起動させることができます
// brew install redis
// redis-server /usr/local/etc/redis.conf --loglevel verbose
JedisPool jedis = new JedisPool("localhost");
config.getMethodsConfig().setMetricsDatastore(new RedisMetricsDatastore("test", jedis));

Slack slack = Slack.getInstance(config);
```

---
## ソケットモード

ソケットモードを使った Slack アプリは、イベント API やインタラクティブコンポーネントを HTTP リクエスト URL を公開することなく、利用できるようになります。

詳細は [Bolt のドキュメントの "Bolt がやっていること" というセクション](/guides/socket-mode)を参考にしてください。

---
## Real Time Messaging (RTM)

**注**: ファイヤーウォール内で Slack イベントを受信する必要があるなどの特殊な制約下以外での RTM API の利用は推奨していません

Real Time Messaging API は Slack からリアルタイムでイベントを受信したり、ユーザとしてメッセージを送信するための WebSocket ベースの API です。単に "RTM API" と呼ばれることもあります。

詳細は「[Real Time Messaging (RTM)](/guides/rtm)」を参考にしてください。

---
## SCIM API

SCIM API は、ユーザアカウントやグループのプロビジョニングのための API 群です。SCIM は、Slack を含む複数のサービス・ツールを横断してユーザアカウントの管理を行うために Single Sign-on (SSO) サービスや ID プロバイダーによって利用されます。

詳細は「[SCIM API](/guides/scim-api)」を参考にしてください。

---
## Audit Logs API

Audit Logs API は Enterprise Grid の OrG 内で発生したイベントをモニタリングするための API 群です。

詳細は「[Audit Logs API](/guides/audit-logs-api)」を参考にしてください。

---
## ステータス API

ステータス API は、Slack サービスの稼働状態の情報を提供する API です。何らかの障害、サービス停止、メンテナンスが発生した場合、影響のある Slack の機能、時系列での詳細なアップデートなど、発生している問題に関する全ての情報が反映されます。

詳細は「[ステータス API](/guides/status-api)」を参考にしてください。

---
## API クライアントのカスタマイズ

**com.slack.api.SlackConfig** には API クライアントをカスタマイズするための様々な設定オプションが提供されています。設定したい内容を反映した **SlackConfig** を生成して **Slack** インスタンスの初期化時に渡すことができます。

```java
import com.slack.api.*;

SlackConfig config = new SlackConfig();
config.setPrettyResponseLoggingEnabled(true);
Slack slack = Slack.getInstance(config);
```

以下は利用可能なカスタマイズ用オプションの一覧です。

|名称|型|説明（デフォルト値）|
|-|-|-|
|**proxyUrl**|**String**|全ての Slack への通信にプロキシサーバーを有効にしたい場合、`http://localhost:8888` のような完全な URL を表現する文字列の値を指定します。 (デフォルト値: null)|
|**prettyResponseLoggingEnabled**|**boolean**|このフラグが true のとき Slack API から受け取ったレスポンスボディの JSON データを整形した上でデバッグレベルでログ出力します （デフォルト値: `false`）|
|**failOnUnknownProperties**|**boolean**|このフラグが true のとき JSON パーサーは Slack API レスポンス内に未知のプロパティを検知したときに例外を throw します （デフォルト値: `false`）|
|**tokenExistenceVerificationEnabled**|**boolean**|このフラグが true のとき **MethodsClient** はトークンが未設定の状態で API 呼び出しをしようとするとその前に例外を throw します （デフォルト値: `false`）|
|**httpClientResponseHandlers**|**List\<HttpResponseListener\>**|**HttpResponseListener** は Web API 呼び出しの後処理として実行される **Consumer\<HttpResponseListener.State\>** 型の関数です。これを使った実装については以下のコード例を参考にしてください （デフォルト値: mutable な空リスト）|
|**auditEndpointUrlPrefix**|**String**|Audit Logs API の URL プレフィクスをカスタマイズしたい場合に上書きします （デフォルト値: `"https://api.slack.com/audit/v1/"`）|
|**methodsEndpointUrlPrefix**|**String**|API メソッドの URL プレフィクスをカスタマイズしたい場合に上書きします （デフォルト値:  `"https://slack.com/api/"`）|
|**scimEndpointUrlPrefix**|**String**|SCIM API の URL プレフィクスをカスタマイズしたい場合に上書きします （デフォルト値: `"https://api.slack.com/scim/v1/"`）|
|**statusEndpointUrlPrefix**|**String**|Status API の URL プレフィクスをカスタマイズしたい場合に上書きします （デフォルト値: `"https://status.slack.com/api/v2.0.0/"`）|
|**legacyStatusEndpointUrlPrefix**|**String**|Status API (v1) の URL プレフィクスをカスタマイズしたい場合に上書きします （デフォルト値: `"https://status.slack.com/api/v1.0.0/"`）|


### Web API コールの後処理

**HttpResponseListener** は Web API 呼び出しの後処理として実行される **Consumer\<HttpResponseListener.State\>** 型の関数です。

この SDK が提供している **ResponsePrettyPrintingListener** はどのように動作するかを見るのにちょうどよい例です。

引数として与えられる **State** という値は、その API リクエスト時に使用された **SlackConfig**、生のレスポンスボディ（文字列）、生の HTTP レスポンスを含みます。

このリスナーは **SlackConfig** の **prettyResponseLoggingEnabled** オプションが true になっているときだけ動作します。そのため、コードの中でそのフラグの値をチェックしています。

```java
import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.*;
import org.slf4j.*;

public class ResponsePrettyPrintingListener extends HttpResponseListener {
  private static final Logger logger = LoggerFactory.getLogger(ResponsePrettyPrintingListener.class);

  @Override
  public void accept(State state) {
    SlackConfig config = state.getConfig();
    String body = state.getParsedResponseBody();
    if (config.isPrettyResponseLoggingEnabled() && body != null && body.trim().startsWith("{")) {
      JsonElement jsonObj = JsonParser.parseString(body);
      String prettifiedJson = GsonFactory.createSnakeCase(config).toJson(jsonObj);
      logger.debug(prettifiedJson);
    }
  }
}
```

このフックを有効にするために、このクラスのインスタンスをランタイムに追加します。

```java
import com.slack.api.*;

SlackConfig config = new SlackConfig();
config.getHttpClientResponseHandlers().add(new ResponsePrettyPrintingListener());
Slack slack = Slack.getInstance(config);
```

以上が Slack API クライアントの基本でした。

もし実際のアプリ開発で、これ以外の設定項目や柔軟性が必要になったときは [GitHub Issues](https://github.com/slackapi/java-slack-sdk/issues) で機能要望をあげてください。なお、こちらへのレポートは必ず英語でお願いします。
