---
layout: ja
title: "ソケットモード"
lang: ja
---

# ソケットモード

[ソケットモード](https://api.slack.com/apis/connections/socket)では、Slack からのペイロードを受け付ける Web エンドポンとを提供するサーバーを構築する代わりに Slack と WebSocket のコネクション経由でやりとりすることができるようになります。この SDK では、バージョン 1.5 から提供されている **bolt-socket-mode** という Bolt の拡張を利用することで、ソケットモードを有効にしたアプリを開発することができます。

### Slack アプリの設定

ソケットモードを有効にするには、[Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Settings** へ遷移します。この画面でいくつかやることがあります。

* **Settings** > **Basic Information** へ遷移
  * **App-Level Token** に [`connections:write`](https://api.slack.com/scopes/connections:write) スコープが付与されたものを一つ追加
  * この `xapp-` から始まるトークンの値を取得してアプリで利用
* **Settings** > **Socket Mode** へ遷移
  * **Enable Socket Mode** を Off から On にする
* それぞれの機能を Request URL なしで設定
* アプリをインストールしてボットトークン・ユーザートークンを取得 (ボット: `xoxb-`, ユーザー: `xoxp-`)

### プロジェクトのセットアップ

ソケットモードのコネクションを管理するために、**bolt-socket-mode** ライブラリに加えて、**javax.websocket-api** と **tyrus-standalone-client (1.x)** が必要です。以下は、必要最小限の Maven 設定ファイルです。インタラクティブな Slack アプリの開発には Bolt を利用することを推奨しますが、内部で利用されている **slack-api-client** と WebSocket 関連のライブラリだけを利用することも可能です。

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>awesome-slack-app</artifactId>
  <version>0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
  <dependencies>
    <dependency>
      <groupId>com.slack.api</groupId>
      <artifactId>bolt-socket-mode</artifactId>
      <version>{{ site.sdkLatestVersion }}</version>
    </dependency>
    <dependency>
      <groupId>javax.websocket</groupId>
      <artifactId>javax.websocket-api</artifactId>
      <version>{{ site.javaxWebsocketApiVersion }}</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.tyrus.bundles</groupId>
      <artifactId>tyrus-standalone-client</artifactId>
      <version>{{ site.tyrusStandaloneClientVersion }}</version>
    </dependency>
  </dependencies>
</project>
```

ソケットモードのコネクション管理インターフェースである `SocketModeClient` は WebSocket でのコミュニケーションレイヤーとして、以下のオープンソースライブラリをサポートしています。もし、これら以外にサポートすべきライブラリの推薦があれば、ぜひ[こちらから](https://github.com/slackapi/java-slack-sdk/issues)フィードバックをお願いします（**英語でお願いします**）。

|ライブラリ名|Maven アーティファクト|
|-|-|
|[Tyrus Standalone Client (default)](https://github.com/eclipse-ee4j/tyrus)|[org.glassfish.tyrus.bundles:tyrus-standalone-client](https://search.maven.org/artifact/org.glassfish.tyrus.bundles/tyrus-standalone-client)|
|[Java-WebSocket](https://github.com/TooTallNate/Java-WebSocket)|[org.java-websocket:Java-WebSocket](https://search.maven.org/artifact/org.java-websocket/Java-WebSocket)|

実装を切り替える場合、`SocketModeClient.Backend.*` を **slack-api-client** の `SocketModeClient` か **bolt-socket-mode** の `SocketModeApp` のコンストラクターに渡します。まt、それ以外の実装が `SocketModeClient` インターフェースを実装しているなら、直接インスタンス化してもよいでしょう。

```java
String appToken = "xapp-";
App app = new App();
SocketModeApp socketModeApp = new SocketModeApp(
  appToken,
  SocketModeClient.Backend.JavaWebSocket,
  app
);
socketModeApp.start();
```

言うまでもないかもしれませんが、その実装が依存する WebSocket ライブラリを追加することも忘れずに。上記の例の場合は ``org.java-websocket:Java-WebSocket`` をこの SDK に加えて追加する必要があります。

---
## コード例

ソケットモードを使う場合であっても、 `App` をインスタンス化して、リスナーを登録する方法は HTTP エンドポイントを立てる方式のときと全く変わりません。

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;

// ここで指定するボットトークンは xoxb- から始まるもので、アプリレベルトークンではありません。
// このトークンは Slack アプリをワークスペースにインストールするとワークスペースごとに発行されるものです。
String botToken = System.getenv("SLACK_BOT_TOKEN");
AppConfig appConfig = AppConfig.builder().singleTeamBotToken(botToken).build();
// デフォルトコンストラクタを使う場合、SLACK_BOT_TOKEN という環境変数に値が設定されていることを期待します。
App app = new App(appConfig);

app.event(AppMentionEvent.class, (req, ctx) -> {
  ctx.say("どうも！");
  return ctx.ack();
});
```

**bolt-socket-mode** ライブラリの `SocketModeApp` クラスは、ソケットモードでのコミュニケーションとのアダプターとなるクラスです。

```java
import com.slack.api.bolt.socket_mode.SocketModeApp;

// `connections:write` スコープが付与されたアプリレベルトークン
String appToken = System.getenv("SLACK_APP_TOKEN");

// アプリレベルトークン、Bolt アプリとともにソケットモードのアダプターを初期化
SocketModeApp socketModeApp = new SocketModeApp(appToken, app);

// #start() メソッドは WebSocket コネクションを確立して、カレントスレッドをブロックし続けます。
// ブロックしたくない場合は #startAsync() を使ってください。
socketModeApp.start();
```

もし、あなたが開発するアプリが任意のワークスペースへの配布アプリであれば、ソケットモードのコネクションとともに OAuth フローをハンドリングするための Web アプリケーションを同じアプリ内で起動することができます。以下のコード例では、一つの Java プロセスがソケットモードのコネクションと Web アプリの両方を管理しています。もし、これらを分けたい場合、インストール情報を管理する `InstallationStore` を共有している限り、別々のアプリとして実行することも可能です。

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;

// これは配布されるソケットモードのアプリであるため
// 特定のワークスペースに紐づくボットトークンや Singing Secret は不要です。
AppConfig appConfig = AppConfig.builder()
  .clientId("111.222")
  .clientSecret("xxx")
  .scope("app_mentions:read,chat:write,commands")
  .oauthInstallPath("install")
  .oauthRedirectUriPath("oauth_redirect")
  .build();

// App を初期化して、リスナーを登録
App app = new App(appConfig);
app.event(AppMentionEvent.class, (req, ctx) -> {
  ctx.say("Hi there!");
  return ctx.ack();
});

// ------------------------------
// ソケットモードアプリを実行するスレッドを開始
import com.slack.api.bolt.socket_mode.SocketModeApp;

String appToken = "xapp-1-A111-111-xxx";
SocketModeApp socketModeApp = new SocketModeApp(appToken, app);

// このメソッドはカレントスレッドをブロックしません
socketModeApp.startAsync();

// ------------------------------
// 埋め込みの Jetty Web サーバーを起動
import com.slack.api.bolt.jetty.SlackAppServer;
import java.util.HashMap;
import java.util.Map;

Map<String, App> apps = new HashMap<>();
apps.put("/slack/", new App(appConfig).asOAuthApp(true));
SlackAppServer oauthSever = new SlackAppServer(apps);

// カレントスレッドをブロックします
oauthSever.start();

// OAuth 開始 URL は https://{your public domain}/slack/install になるので、ブラウザからアクセス
```

### Bolt がやっていること

上記のコードによって実際に何が起きているのかに興味があるなら、`SocketModeClient` だけを実行してみて、そのデバッグレベルのログを追うと何が起きているかを理解するのに役立つかもしれません。なお、ここでのコード例はプリミティブなモジュールを直接利用しているので、エンベロープの種別を判定したり、ペイロードを自前でパースしたりする必要があります。

```java
import com.slack.api.Slack;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.SocketModeResponse;

String appLevelToken = "xapp-A111-222-xxx";

// WSS URL を発行して、その URL をこのクライアントに設定
try (SocketModeClient client = Slack.getInstance().socketMode(appLevelToken)) {
  // SocketModeClient は #close() メソッドを持っています

  // 生の WebSocket テキストメッセージをハンドルするリスナーを追加
  // エンベロープだけでなく、"hello" などその他のメッセージを受け取れます
  client.addWebSocketMessageListener((String message) -> {
    // TODO: WebSocket のテキストメッセージを使って何かする
  });

  client.addWebSocketErrorListener((Throwable reason) -> {
    // TODO: 例外を処理する
  });

  // type: events のエンベロープだけを受け取るリスナーを追加
  client.addEventsApiEnvelopeListener((EventsApiEnvelope envelope) -> {
    // TODO: Events API のペイロードを使って何ややる

    // リクエストに応答を返す（3 秒以内）
    SocketModeResponse ack = AckResponse.builder().envelopeId(envelope.getEnvelopeId()).build();
    client.sendSocketModeResponse(ack);
  });

  client.connect(); // ソケットモードサーバーに接続してメッセージの受信を開始

  client.disconnect(); // ソケットモードサーバーから切断

  client.connectToNewEndpoint(); // 新しい WSS URL を発行して、その URL に接続
}
```
