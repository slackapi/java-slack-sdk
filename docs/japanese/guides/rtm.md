---
lang: ja
---

# Real Time Messaging (RTM)

[Real Time Messaging API](https://docs.slack.dev/legacy/legacy-rtm-api) は Slack からリアルタイムでイベントを受信したり、ユーザとしてメッセージを送信するための WebSocket ベースの API です。単に "RTM API” と呼ばれることもあります。

**注**: RTM API は最新の権限（Granular Permissions）を持ったアプリでは利用できません。移行先として [Events API](/guides/events-api) や [Web API](/guides/web-api-basics) を利用することを推奨します。ファイヤーウォールなどの制約により、止むを得ず RTM API を使う必要がある場合は、[この URL](https://api.slack.com/apps?new_classic_app=1) から Classic Permissions の Slack アプリをつくれば、新規のアプリでも引き続き RTM を利用できます。もし既存の RTM に依存したアプリを使っている場合、そのアプリの権限管理を新しい方式に移行すると RTM を使えなくなりますので注意してください。

---
## Prerequisites

RTM クライアントを使うためには、**slack-api-client** ライブラリに加えて、**javax.websocket-api** と **tyrus-standalone-client (v1.x)** も必要です。こちらは必要最低限の Maven 設定ファイルの例です。

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
      <artifactId>slack-api-client</artifactId>
      <version>sdkLatestVersion</version>
    </dependency>
    <dependency>
      <groupId>javax.websocket</groupId>
      <artifactId>javax.websocket-api</artifactId>
      <version>javaxWebsocketApiVersion</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.tyrus.bundles</groupId>
      <artifactId>tyrus-standalone-client</artifactId>
      <version>tyrusStandaloneClientVersion</version>
    </dependency>
  </dependencies>
</project>
```

---
## WebSocket 経由でイベント受信

以下は最低限の動作するイベントハンドラー例です。

```java
import com.slack.api.Slack;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.*;

// 受信したイベントをその type に応じてディスパッチします
RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();

// イベントハンドラーを登録します
RTMEventHandler<UserTypingEvent> userTyping = new RTMEventHandler<UserTypingEvent>() {
  @Override
  public void handle(UserTypingEvent event) {
    // ここで何かする
  }
};
dispatcher.register(userTyping);

String botToken = System.getenv("SLACK_BOT_TOKEN");
Slack slack = Slack.getInstance();

// 有効な WSS URL とともにクライアントを初期化します
RTMClient rtm = slack.rtmConnect(botToken);
// WebSocket コネクションを確立してイベントの受信を開始します
rtm.connect();

// イベントディスパッチャーを有効化します
rtm.addMessageHandler(dispatcher.toMessageHandler());

// イベントハンドラーを実行時に登録解除します
dispatcher.deregister(userTyping);

// WebSocket コネクション越しでメッセージを投稿します
String channelId = "C1234567";
String message = Message.builder().id(1234567L).channel(channelId).text(":wave: Hi there!").build().toJSONString();
rtm.sendMessage(message);

// "presence_change" イベントを受信
String userId = "U1234567";
String presenceQuery = PresenceQuery.builder().ids(Arrays.asList(userId)).build().toJSONString();
rtm.sendMessage(presenceQuery);
String presenceSub = PresenceSub.builder().ids(Arrays.asList(userId)).build().toJSONString();
rtm.sendMessage(presenceSub);

// WebSocket コネクションを強制的に再接続する少し重たい処理（Rate Limits にも注意）
rtm.reconnect();

// Slack との接続を切断 - #close() メソッドもこれを呼び出します
rtm.disconnect();
```
