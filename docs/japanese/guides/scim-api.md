# SCIM API

[SCIM API](/admins/scim-api) は、ユーザアカウントやグループのプロビジョニングのための API 群です。SCIM は、Slack を含む複数のサービス・ツールを横断してユーザアカウントの管理を行うために Single Sign-on (SSO) サービスや ID プロバイダーによって利用されます。

[SCIM (System for Cross-domain Identity Management)](http://www.simplecloud.info/) は、数多くのサービスでサポートされている仕様です。それに準拠するこの API は他の Slack API とは若干異なるふるまいをします。

詳細は「[SCIM API（英語）](/admins/scim-api)」を確認してください。

---
## SCIM API を Java で使う

**slack-api-client** を使った SCIM API の呼び出しは、以下のコードのようなわかりやすいアプローチです。他の API クライアントと同様に **Slack** オブジェクトのメソッドで API クライアントを生成します。

```java
import com.slack.api.Slack;
import com.slack.api.scim.*;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `admin` scope が必要
SCIMClient scim = slack.scim(token);
```

**SCIMClient** は SCIM API で提供されている全てのエンドポイントをサポートしています。その一覧については [Javadoc](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/sdkLatestVersion/slack-api-client-sdkLatestVersion-javadoc.jar/!/com/slack/api/scim/SCIMClient.html) を確認してください。このページでは、実際にどのように使うかを示すいくつかのコード例を紹介します。

### ユーザーの管理

以下の Java コードは SCIM API によるユーザーの管理の例です。

```java
import com.slack.api.Slack;
import com.slack.api.scim.*;
import com.slack.api.scim.model.*;
import com.slack.api.scim.response.*;

// ユーザーを検索
UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1000));

// ユーザー情報を取得
final String userId = users.getResources().get(0).getId();
UsersReadResponse read = slack.scim(token).readUser(req -> req.id(userId));

// ユーザー検索のページング
UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1).startIndex(2));
users.getItemsPerPage(); // 1
users.getResources().size(); // 1
users.getStartIndex(); // 2

// ユーザーの新規作成
User newUser = new User();
newUser.setName(new User.Name());
newUser.getName().setGivenName("Kazuhiro");
newUser.getName().setFamilyName("Sera");
// 他のフィールドも設定して...
UsersCreateResponse creation = slack.scim(token).createUser(req -> req.user(newUser));

// ユーザー検索でフィルター条件を持つクエリを実行
// /admins/scim-api
UsersSearchResponse searchResp = slack.scim(token).searchUsers(req -> req
  .count(1)
  .filter("userName eq \"" + userName + "\"")
);

// ユーザーを削除
UsersDeleteResponse deletion = slack.scim(token).deleteUser(req -> req.id(userId));
```

### グループの管理

以下の Java コードは SCIM API によるグループの管理の例です。

```java
import com.slack.api.scim.request.*;

// グループの新規作成
Group newGroup = new Group();
newGroup.setDisplayName("Awesome Group");
slack.scim(token).createGroup(req -> req.group(newGroup));

// グループの検索
GroupsSearchResponse groups = slack.scim(token).searchGroups(req -> req.count(1000));

// グループ検索のページング
GroupsSearchResponse pagination = slack.scim(token).searchGroups(req -> req.count(1));
pagination.getResources().size(); // 1
pagination.getResources().size(); // 1

// 一部の属性の書き換え
GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
User user = slack.scim(token).searchUsers(req -> req.count(1)).getResources().get(0);
memberOp.setValue(user.getId());
memberOp.setOperation("delete");
op.setMembers(Arrays.asList(memberOp));
slack.scim(token).patchGroup(req -> req.id(group.getId()).group(op));

// グループの全ての属性を一括で上書き（一部の項目が指定されていない場合は未設定に上書き）
slack.scim(token).updateGroup(req -> req.id(groupId).group(group));
```

### エラーハンドリング

**SCIMClient** のメソッドは SCIM API からエラーを返されたときに **SCIMApiException** を throw する可能性があります。

```java
// エラーへの対処
try {
  slack.scim("dummy").searchUsers(req -> req.count(1000));
} catch (SCIMApiException e) {
  e.getMessage(); // "status: 401, description: invalid_authentication"
  e.getResponse(); // HTTP レスポンスオブジェクト (okhttp)
  e.getResponseBody(); // 生のレスポンスボディ（文字列）
  e.getError(); // レスポンスボディをデシリアライズしたエラー情報
  e.getError().getErrors().getCode(); // 401
  e.getError().getErrors().getDescription(); // "invalid_authentication"
}
```

---
## Rate Limits

Slack の SCIM API は、期待通りの快適なユーザー体験を提供するために、その [Rate Limits](/apis/web-api/rate-limits) に依拠しています。この制限は他の Slack API の制限とは異なり、アプリ単位ではなく、一つの OrG に設定されている全ての SCIM API 利用アプリ全体に対して適用されることにご注意ください。詳細は [API ドキュメント](/admins/scim-api#limitations)を参考にしてください。

**AsyncSCIMClient** （非同期クライアント）はその実行において Rate Limits を考慮します。

非同期クライアントは、可能な限りバーストリクエストを発生させないために、内部にキューの仕組みを持っています。一方、**SCIMClient** （同期クライアント）はそのような考慮はなく、常に即時でリクエストを送信します。幸いにもこれらの同期・非同期クライアントは協調して **MetricsDatastore** 内のメトリクスを更新します。これにより、非同期クライアントは、今どれくらいのトラフックを Slack プラットフォームに送っているかを正確に把握し、残っている呼び出し量を推測することができます。

このデータストアのデフォルトの実装は、JVM のヒープメモリを使用したインメモリデータベースです。デフォルトの **SlackConfig** はこのインメモリ実装を有効にします。これはほとんどのケースで良い具合に動作するでしょう。この設定で問題ないなら、特に追加で何か設定する必要はありません。

**AsyncSCIMClient** はそのメトリクスデータを考慮しながら動作します。アプリケーション内の API クライアントがすでに大量のリクエストを短時間の間に送信していると判断した場合、エラーにならないように API リクエストの実行を遅延させることがあります。

```java
import com.slack.api.Slack;
import com.slack.api.scim.response.*;
import java.util.concurrent.CompletableFuture;

Slack slack = Slack.getInstance();
String token = "xoxp-***"; // OrG 管理者のユーザートークン

CompletableFuture<UsersSearchResponse> users = slack.scimAsync(token).searchUsers(req -> req
  .startIndex(1)
  .count(100)
  .filter("userName Eq \"Carly\"")
);
```