---
lang: ja
---

# ステータス API

[ステータス API](https://api.slack.com/docs/slack-status) は、Slack サービスの稼働状態の情報を提供する API です。何らかの障害、サービス停止、メンテナンスが発生した場合、影響のある Slack の機能、時系列での詳細なアップデートなど、発生している問題に関する全ての情報が反映されます。

---
## Java での利用

ステータス API から返されるレスポンスのデータ構造は非常にシンプルです。Java で表現すると以下のようになります。

```java
class CurrentStatus {
  String status;
  String dateCreated;
  String dateUpdated;
  List<SlackIssue> activeIncidents;
}

class SlackIssue {
  Integer id;
  String dateCreated;
  String dateUpdated;
  String title;
  String type;
  String status;
  String url;
  List<String> services;
  List<Note> notes;
}

class Note {
  String dateCreated;
  String body;
}
```

---
## Current Status API を Java で利用

他の API クライアントと同様 **Slack** クラスの `status()` というメソッドで API クライアントを生成し、そのクライアントの `current()` メソッドで API に対する HTTP リクエストを実行します。

```java
import com.slack.api.Slack;
import com.slack.api.status.v2.model.CurrentStatus;

Slack slack = Slack.getInstance();
CurrentStatus status = slack.status().current();
```

---
## History API を Java で利用

上の `current()` と同じように `history()` メソッドの呼び出しで API に対して HTTP リクエストを送信します。

```java
import com.slack.api.Slack;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.status.v2.model.SlackIssue;

Slack slack = Slack.getInstance();
List<SlackIssue> events = slack.status().history();
```
