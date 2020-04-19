---
layout: ja
title: "Audit Logs API"
lang: ja
---

# Audit Logs API

[Audit Logs API](https://api.slack.com/docs/audit-logs-api) は [Enterprise Grid](https://api.slack.com/enterprise/grid) の [OrG](https://slack.com/intl/ja-jp/help/articles/360004150931) 内で発生したイベントをモニタリングするための API 群です。

Audit Logs API は、各種 SIEM (Security Information and Event Management) ツールによって Slack の OrG がどのようにアクセスされているかの分析結果を提供するために利用されることがあります。アプリケーションを開発して OrG のメンバーがどのように Slack を使っているかを確認する用途にも利用できます。

Audit Logs API で利用できるトークンの取得は「[Monitoring workspace events with the Audit Logs API（英語）](https://api.slack.com/docs/audit-logs-api)」で解説されている手順に従ってください。Audit Logs API を利用する Slack アプリは、通常のアプリのように個々のワークスペース単位でインストールするのではなく OrG レベルでインストールする必要があります。

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

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/com/slack/api/audit/response/LogsResponse.html) を確認するとよいでしょう。

### getSchemas()

このエンドポイントはオブジェクトの種類についての情報を、全てのオブジェクトのリストとそれぞれの短い説明とともに応答します。実行に認証は必要ありません。

```java
import com.slack.api.audit.response.SchemasResponse;

SchemasResponse response = audit.getSchemas();
```

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/com/slack/api/audit/response/SchemasResponse.html) を確認するとよいでしょう。

### getActions()

このエンドポイントはアクションの種類についての情報を、[すべてのアクションのリスト](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/com/slack/api/audit/Actions.html)とそれぞれの説明とともに応答します。実行に認証は必要ありません。

```java
import com.slack.api.audit.response.ActionsResponse;

ActionsResponse response = audit.getActions();
```

レスポンスのデータ構造を知りたい場合は [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/com/slack/api/audit/response/ActionsResponse.html) を確認するとよいでしょう。
