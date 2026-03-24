# Audit Logs API

[Audit Logs API](/admins/audit-logs-api) は [Enterprise Grid](/enterprise) の [OrG](https://slack.com/intl/ja-jp/help/articles/360004150931) 内で発生したイベントをモニタリングするための API 群です。

Audit Logs API は、各種 SIEM (Security Information and Event Management) ツールによって Slack の OrG がどのようにアクセスされているかの分析結果を提供するために利用されることがあります。アプリケーションを開発して OrG のメンバーがどのように Slack を使っているかを確認する用途にも利用できます。

Audit Logs API で利用できるトークンの取得は「[Monitoring workspace events with the Audit Logs API（英語）](/admins/audit-logs-api)」で解説されている手順に従ってください。Audit Logs API を利用する Slack アプリは、通常のアプリのように個々のワークスペース単位でインストールするのではなく OrG レベルでインストールする必要があります。

---
## Audit Logs API を Java で利用

**slack-api-client** を使った Audit Logs API の呼び出しは、以下のコードのようなわかりやすいアプローチです。

```java
import com.slack.api.Slack;
import com.slack.api.audit.*;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope が必要
AuditClient audit = slack.audit(token);
```

全てのエンドポイントに対応しています。

|Java メソッド|HTTP メソッドとエンドポイントのパス|
|-|-|
|**AuditClient#getLogs()**|**GET /logs**|
|**AuditClient#getSchemas()**|**GET /schemas**|
|**AuditClient#getActions()**|**GET /actions**|

### getLogs()

これは主に利用されるエンドポイントで、条件に基づいて OrG 内での監査イベントの一覧を取得することができます。アプリがインストールされた OrG やそのワークスペースで発生したアクションのリストを返します。実行には認証が必要です。

```java
import com.slack.api.audit.response.LogsResponse;

LogsResponse response = audit.getLogs(req -> req
  .oldest(1521214343) // 応答に含める監査イベントの最も直近な UNIX 時間 (inclusive)
  .action(Actions.User.user_login) // メンバーがログインしたイベントを指定
  .limit(10) // 結果の最大取得件数
);
```

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/LogsResponse.html) を確認するとよいでしょう。

### getSchemas()

このエンドポイントはオブジェクトの種類についての情報を、全てのオブジェクトのリストとそれぞれの短い説明とともに応答します。実行に認証は必要ありません。

```java
import com.slack.api.audit.response.SchemasResponse;

SchemasResponse response = audit.getSchemas();
```

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/SchemasResponse.html) を確認するとよいでしょう。

### getActions()

このエンドポイントはアクションの種類についての情報を、[すべてのアクションのリスト](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/Actions.html)とそれぞれの説明とともに応答します。実行に認証は必要ありません。

```java
import com.slack.api.audit.response.ActionsResponse;

ActionsResponse response = audit.getActions();
```

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/audit/response/ActionsResponse.html) を確認するとよいでしょう。

---
## Rate Limits

Slack の Audit Logs API は、期待通りの快適なユーザー体験を提供するために、その [Rate Limits](/apis/web-api/rate-limits) に依拠しています。全ての API メソッドは Tier 3（毎分 50 回以内 / 瞬間的なバーストトラフィックは許容）の Rate Limit が適用されます。詳細は [API ドキュメント](/admins/audit-logs-api)を参考にしてください。

**AsyncAuditClient** （非同期クライアント）はその実行において Rate Limits を考慮します。

非同期クライアントは、可能な限りバーストリクエストを発生させないために、内部にキューの仕組みを持っています。一方、**AuditClient** （同期クライアント）はそのような考慮はなく、常に即時でリクエストを送信します。幸いにもこれらの同期・非同期クライアントは協調して **MetricsDatastore** 内のメトリクスを更新します。これにより、非同期クライアントは、今どれくらいのトラフックを Slack プラットフォームに送っているかを正確に把握し、残っている呼び出し量を推測することができます。

このデータストアのデフォルトの実装は、JVM のヒープメモリを使用したインメモリデータベースです。デフォルトの **SlackConfig** はこのインメモリ実装を有効にします。これはほとんどのケースで良い具合に動作するでしょう。この設定で問題ないなら、特に追加で何か設定する必要はありません。

**AsyncAuditClient** はそのメトリクスデータを考慮しながら動作します。アプリケーション内の API クライアントがすでに大量のリクエストを短時間の間に送信していると判断した場合、エラーにならないように API リクエストの実行を遅延させることがあります。

```java
import com.slack.api.audit.*;
import com.slack.api.audit.response.LogsResponse;
import java.util.concurrent.CompletableFuture;

String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope が必要
AsyncAuditClient audit = Slack.getInstance().auditAsync(token);

CompletableFuture<LogsResponse> response = audit.getLogs(req -> req
  .oldest(1521214343) // 応答に含める監査イベントの最も直近な UNIX 時間 (inclusive)
  .action(Actions.User.user_login) // メンバーがログインしたイベントを指定
  .limit(10) // 結果の最大取得件数
);
```