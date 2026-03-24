# アプリの配布 (OAuth)

新しく作られた Slack アプリは、はじめは開発用ワークスペース（Development Workspace）にだけインストールすることができます。OAuth Redirect URL を設定して [App Distribution](/app-management/distribution) を有効にすると、そのアプリは他のどのワークスペースにもインストールできるようになります。

* 「[Installing with OAuth（英語）](/authentication/installing-with-oauth)」
* 「[Distributing Slack Apps（英語）](/app-management/distribution)」

### Slack アプリの設定

アプリの配布を有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Settings** > **Manage Distribution** へ遷移します。ページの説明に従って設定を行います。

**Redirect URL** については Bolt for Java では `https://{あなたのドメイン}/slack/oauth/callback` のような URL で応答します。この URL を変更する方法などはこのページのあとのセクションの一覧を参照してください。

Bolt for Java は、バージョン 1.4.0 から自動的に [OrG 全体へのインストール機能](/enterprise)に対応しています。OrG 全体へのインストールは、Slack アプリ管理画面の **Org Level Apps** にある設定から有効にしてください。

### Bolt アプリがやること

Bolt アプリが OAuth フローをハンドルするためにやらなければならないことは以下の通りです。

* ユーザーを Slack の Authorize エンドポイントに必要なパラメーターとともに誘導する OAuth フロー開始のエンドポイントを提供する
  * `state` パラメーターの値を生成して、後ほどその検証を行う
  * `client_id`, `scope`, `user_scope` (v2 のみ), `state` パラメーターを URL に付加する
* Slack からリダイレクトされてきたユーザーリクエストを処理するエンドポイントを提供する
  * `state` パラメーターが正当かを検証する
  * [oauth.v2.access](/reference/methods/oauth.v2.access) (レガシーアプリの場合は [oauth.access](/reference/methods/oauth.access)) API メソッドを呼び出してトークンを発行し、それを保存することでインストールを完了させる
* インストールを実行したユーザーを誘導する完了・エラーページを用意する
  * これらの URL は通常別のどこかであることが多いが、Bolt アプリがそれをサーブすることも可能

---
## コード例

以下は OAuth フローの実装例です。OAuth フローのハンドリング機能は多くのカスタムアプリには必要ないので、Bolt のデフォルトの状態ではこの機能は無効化されています。**App** インスタンスは、それを有効にする場合は `asOAuthApp(true)` を明に呼び出す必要があります。

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

// Slack API からのペイロードリクエストを処理する App
// 環境変数 SLACK_SIGNING_SECRET が存在する前提
App apiApp = new App();
apiApp.command("/hi", (req, ctx) -> {
  return ctx.ack("Hi there!");
});

// OAuth フローを処理する App
// 以下の環境変数が設定されている前提:
//   SLACK_CLIENT_ID, SLACK_CLIENT_SECRET, SLACK_REDIRECT_URI, SLACK_SCOPES,
//   SLACK_INSTALL_PATH, SLACK_REDIRECT_URI_PATH
//   SLACK_OAUTH_COMPLETION_URL, SLACK_OAUTH_CANCELLATION_URL
App oauthApp = new App().asOAuthApp(true);

// これら二つの App をルーとパスの指定とともにマウント
SlackAppServer server = new SlackAppServer(new HashMap<>(Map.ofEntries(
  entry("/slack/events", apiApp), // POST /slack/events (Slack API からのリクエストのみ)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (ユーザーがブラウザーでアクセス)
)));

server.start(); // http://localhost:3000
```

技術的には、一つの **App** を Slack API からのリクエストと OAuth フローにおけるユーザーインタラクションの両方に使うことは可能です。しかし、ほとんどのアプリは OAuth フローのためのルートパスは別のものにすると想定されるので、上の例はそのようになっています。

### 配布可能な Slack アプリのための設定

以下は配布可能なアプリのための設定項目の一覧です。もしこれら以外の環境変数名や、別の読み込みの仕組みを使いたい場合は、自前で **AppConfig** を初期化する実装を行ってください。

|環境変数名|説明 (値を見つけられる場所)|
|-|-|
|**SLACK_SIGNING_SECRET**|**Signing Secret**: リクエスト検証のための秘密キー (**Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_ID**|**OAuth 2.0 Client ID** (**Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_SECRET**|**OAuth 2.0 Client Secret** (**Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_REDIRECT_URI**|**OAUth 2.0 Redirect URI** (**Features** > **OAuth & Permissions** > **Redirect URLs**)|
|**SLACK_SCOPES**|**カンマ区切りの bot scope リスト**: `scope` パラメーターは `https://slack.com/oauth/authorize` や `https://slack.com/oauth/v2/authorize` にクエリパラメーターとして付加されます (**Settings** > **Manage Distribution** > **Sharable URL** から `scope` の値を取得)|
|**SLACK_USER_SCOPES** (v2 のみ)|**カンマ区切りの user scope リスト**: `user_scope` パラメーターは `https://slack.com/oauth/v2/authorize` にクエリパラメーターとして付加されます (**Settings** > **Manage Distribution** > **Sharable URL**, から `user_scope` の値を取得)|
|**SLACK_INSTALL_PATH**|**OAuth フローの開始点**: このエンドポイントはユーザーを `client_id`, `scope`, `user_scope` (v2 のみ), `state` とともに Slack の Authorize エンドポイントにリダイレクトします。推奨するパスは `/slack/oauth/start` ですが、どのようなパスでも構いません。|
|**SLACK_REDIRECT_URI_PATH**|**OAuth Redirect URI**: このエンドポイントは Slack の OAuth 許可確認画面からの callback リクエストを処理します。このパスは **SLACK_REDIRECT_URI** の値と整合している必要があります。推奨のパスは `/slack/oauth/callback` ですが、どのようなパスでも構いません。|
|**SLACK_OAUTH_COMPLETION_URL**|**Installation Completion URL**: インストール完了画面の URL を指定します。どんな URL でも構いません。|
|**SLACK_OAUTH_CANCELLATION_URL**|**Installation Cancellation/Error URL**: キャンセルやエラーが発生したときの遷移先 URL を指定します。どんな URL でも構いません。|

### ストレージサービスの選択

デフォルトでは OAuth フローをサポートする Bolt アプリは、ローカルファイルシステムを state パラメーターやトークンの保存先に使用します。Bolt はそれ以外にも以下の選択肢に標準で対応しています。

* ローカルファイルシステム
* Amazon S3
* リレーショナルデータベース (via JDBC) - [_近日対応予定_](https://github.com/slackapi/java-slack-sdk/issues/347)

もし使いたいデータストアがサポートされていない場合でも **com.slack.api.bolt.service.InstallationService** と **com.slack.api.bolt.service.OAuthStateService** の interface を実装すれば利用することができます。

以下は Amazon S3 をバックエンドとして使用したサンプル例です。

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

// 標準的な AWS の環境変数が設定済であることが前提
// export AWS_REGION=us-east-1
// export AWS_ACCESS_KEY_ID=AAAA*************
// export AWS_SECRET_ACCESS_KEY=4o7***********************

// この bucket のセキュリティポリシーには十二分にご注意ください
String awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE";

InstallationService installationService = new AmazonS3InstallationService(awsS3BucketName);
// 全てのインストール結果を別のレコードとして保存したい場合は true をセット
installationService.setHistoricalDataEnabled(true);

// apiApp はトークンの取得のために InstallationService だけを利用します
App apiApp = new App();
apiApp.command("/hi", (req, ctx) -> {
  return ctx.ack("Hi there!");
});
apiApp.service(installationService);

// 言うまでもなく oauthApp は InstallationService を利用します
// それに加えて state パラメーターの生成/参照/廃棄のために OAuthStateService を利用します
App oauthApp = new App().asOAuthApp(true);
oauthApp.service(installationService);

// state パラメーターの値を Amazon S3 ストレージに保存
OAuthStateService stateService = new AmazonS3OAuthStateService(awsS3BucketName);
// このサービスは OAuth フローに対応する App のみに必要
oauthApp.service(stateService);

// ルーとパスとともに二つの App をマウント
SlackAppServer server = new SlackAppServer(new HashMap<>(Map.ofEntries(
  entry("/slack/events", apiApp), // POST /slack/events (Slack API からのリクエストのみ)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (ユーザーがブラウザーでアクセス)
)));

server.start(); // http://localhost:3000
```

もし[トークンローテーション](/authentication/using-token-rotation)を有効にしたいという場合は、あなたの `InstallationService` がトークンローテーション互換である必要があります。詳細は [v1.9.0 のリリースノート（英語）](https://github.com/slackapi/java-slack-sdk/releases/tag/v1.9.0)を参考にしてください。

### Granular Permission Apps と Classic Apps

Slack アプリインストールには、二つの OAuth フローがあります。V2（ちょっと紛らわしいですが OAuth のバージョンではなく Slack OAuth フローのバージョンです）の OAuth フローでの Slack アプリは（特にボットユーザーの権限に関して）旧来に比べてより詳細な必要最小限の権限だけをリクエストできるようになりました。二つのやり方の違いは `v2` を Authorization URL やトークンを発行する API メソッドの URL に含んでいることと、API レスポンスのデータ構造に若干の変更が加わっていることです。

#### [V2 OAuth 2.0 フロー](/authentication/installing-with-oauth) (デフォルト)

|-|-|
|Authorization URL|`https://slack.com/oauth/v2/authorize`|
|トークン発行の API メソッド|[`oauth.v2.access`](/reference/methods/oauth.v2.access) ([レスポンス](https://github.com/slackapi/java-slack-sdk/blob/main/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthV2AccessResponse.java))|

#### [Classic OAuth フロー](/authentication/installing-with-oauth)

|-|-|
|Authorization URL|`https://slack.com/oauth/authorize`|
|トークン発行の API メソッド|[`oauth.access`](/reference/methods/oauth.access) ([レスポンス](https://github.com/slackapi/java-slack-sdk/blob/main/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthAccessResponse.java))|

デフォルトでは Classic OAuth ではなく V2 の OAuth フローが有効になっています。Classic OAuth に対応させるためには **AppConfig** の setter メソッドで `classicAppPermissionsEnabled` を true に設定します。

```java
AppConfig appConfig = new AppConfig();
appConfig.setClassicAppPermissionsEnabled(true);
App app = new App(appConfig);
```

**InstallationService** はレスポンス構造の差異を吸収してくれます。そのため、Class OAuth と V2 を切り替えるときにアプリケーション側では何も変更する必要がありません。

#### Spring Boot で実装する

Spring Boot を用いて Slack OAuth フローを実装するのは非常に簡単です。やらなければならないことは、 1) 必要な環境変数を読み込む 2) サービス、リスナーを登録した `App` インスタンスを Spring Bean として初期化する 3) リクエストをハンドリングする HTTP エンドポイントを 3 つ定義する、この三つだけです。**Bolt は Spring Boot 2.2 以上のバージョンで動作しますので、バージョンにご注意ください。**

```java
package hello;

// export SLACK_SIGNING_SECRET=xxx
// export SLACK_CLIENT_ID=111.222
// export SLACK_CLIENT_SECRET=xxx
// export SLACK_SCOPES=commands,chat:write.public,chat:write
// export SLACK_USER_SCOPES=
// export SLACK_INSTALL_PATH=/slack/install
// export SLACK_REDIRECT_URI_PATH=/slack/oauth_redirect
// export SLACK_OAUTH_COMPLETION_URL=https://www.example.com/completion
// export SLACK_OAUTH_CANCELLATION_URL=https://www.example.com/cancellation

import com.slack.api.bolt.App;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {
  @Bean
  public App initSlackApp() {
    App app = new App().asOAuthApp(true); // `asOAuthApp(true)` をここで呼び出すのを忘れないようにしてください
    app.command("/hello-oauth-app", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}

import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.bolt.servlet.SlackOAuthAppServlet;

@WebServlet("/slack/events")
public class SlackEventsController extends SlackAppServlet {
  public SlackEventsController(App app) { super(app); }
}
@WebServlet("/slack/install")
public class SlackOAuthInstallController extends SlackOAuthAppServlet {
  public SlackOAuthInstallController(App app) { super(app); }
}
@WebServlet("/slack/oauth_redirect")
public class SlackOAuthRedirectController extends SlackOAuthAppServlet {
  public SlackOAuthRedirectController(App app) { super(app); }
}
```

もし `InstallationService` や `OAuthStateService` の他の実装を使用したい場合は、以下のように Spring のコンポーネントを設定してください。

```java
public class SlackApp {
  // この bucket のセキュリティポリシーには十二分にご注意ください
  private static final String S3_BUCKET_NAME = "your-s3-bucket-name";
  @Bean
  public InstallationService initInstallationService() {
    InstallationService installationService = new AmazonS3InstallationService(S3_BUCKET_NAME);
    installationService.setHistoricalDataEnabled(true);
    return installationService;
  }
  @Bean
  public OAuthStateService initStateService() {
    return new AmazonS3OAuthStateService(S3_BUCKET_NAME);
  }
  @Bean
  public App initSlackApp(InstallationService installationService, OAuthStateService stateService) {
    App app = new App().asOAuthApp(true);
    app.service(installationService);
    app.service(stateService);
    return app;
  }
}
```

#### 組み込みの tokens_revoked / app_uninstalled イベントハンドラーを利用する

あなたの顧客やエンドユーザーのデータを安全に管理するために [tokens_revoked](/reference/events/tokens_revoked) と [app_uninstalled](/reference/events/app_uninstalled) というイベントを適切にハンドリングすることは非常に重要です。Bolt for Java は、これらのイベントのための組み込みのハンドラーを提供しており、それは `InstallationService` が提供するデータ削除系メソッドとシームレスに連携します。

```java
App app = new App();
InstallationService installationService = new MyInstallationService();
app.service(installationService);
// 組み込みのイベントハンドラーを有効にします
app.enableTokenRevocationHandlers();
```

上記のコードは以下のコード例と等価です：

```java
App app = new App();
InstallationService installationService = new MyInstallationService();
app.service(installationService);
// 組み込みのイベントハンドラーを有効にします
app.event(TokensRevokedEvent.class, app.defaultTokensRevokedEventHandler());
app.event(AppUninstalledEvent.class, app.defaultAppUninstalledEventHandler());
```

あなたが実装したカスタムの `InstallationService` の実装クラスをこれらの組み込みのイベントハンドラーと連携して動作させるためには、[`InstallationService` インターフェース](https://github.com/seratch/java-slack-sdk/blob/main/bolt/src/main/java/com/slack/api/bolt/service/InstallationService.java)に定義されている以下のメソッドを適切に実装してください：

* `void deleteBot(Bot bot)`
* `void deleteInstaller(Installer installer)`
* `void deleteAll(String enterpriseId, String teamId)`

#### 完了・エラーページを Bolt アプリでサーブする

ほとんどのアプリは、完了・エラーページに静的なページを選択するかとは思いますが、これらの URL を Bolt アプリで動的に応答することも可能です。Bolt は Web ページをレンダリングするための機能は何も提供しません。お好みのテンプレートエンジンを使ってください。

```java
String renderCompletionPageHtml(String queryString) { return null; }
String renderCancellationPageHtml(String queryString) { return null; }

oauthApp.endpoint("GET", "/slack/oauth/completion", (req, ctx) -> {
  return Response.builder()
    .statusCode(200)
    .contentType("text/html")
    .body(renderCompletionPageHtml(req.getQueryString()))
    .build();
});

oauthApp.endpoint("GET", "/slack/oauth/cancellation", (req, ctx) -> {
  return Response.builder()
    .statusCode(200)
    .contentType("text/html")
    .body(renderCancellationPageHtml(req.getQueryString()))
    .build();
});
```
