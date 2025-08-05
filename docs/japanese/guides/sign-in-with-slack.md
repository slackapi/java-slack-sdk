# Slack でログインする (OpenID Connect)

[Slack でログインする (Sign in with Slack)](/authentication/sign-in-with-slack/)という機能は、ユーザーが Slack アカウントを使って他のサービスにログインすることに役立ちます。このプラットフォーム機能は、標準の [OpenID Connect](https://openid.net/connect/) の仕様と互換性を持つように最近アップグレードされました。Bolt for Java の 1.10 以上のバージョンであれば、この認証フローを非常に簡単に実装することができます。

### Slack アプリの設定

新しい Slack アプリをつくるときに、以下のユーザースコープを設定してください：

```yaml
oauth_config:
  redirect_urls:
    - https://example.com/replace-this-with-your-own-redirect-uri
  scopes:
    user:
      - openid   # 必須
      - email    # オプショナル
      - profile  # オプショナル
```


### OpenID Connect アプリのための設定

以下は OpenID Connect 互換のアプリのための設定項目の一覧です。もしこれら以外の環境変数名や、別の読み込みの仕組みを使いたい場合は、自前で **AppConfig** を初期化する実装を行ってください。

|環境変数名|説明 (値を見つけられる場所)|
|-|-|
|**SLACK_CLIENT_ID**|**Client ID** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_SECRET**|**Client Secret** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_REDIRECT_URI**|**Redirect URI** (Configure at **Features** > **OAuth & Permissions** > **Redirect URLs**)|
|**SLACK_USER_SCOPES**|**カンマ区切りの user scope リスト**: `scope` パラメーターは `https://slack.com/openid/connect/authorize` にクエリパラメーターとして付加されます。可能な値は `openid`, `email`, `profile` です。|
|**SLACK_INSTALL_PATH**|**OpenID Connect フローの開始点**: このエンドポイントはユーザーを `client_id`, `scope`, `state`, `nonce` (オプショナル) のクエリパラメーターとともに Slack の OpenID Connect エンドポイントにリダイレクトします。|
|**SLACK_REDIRECT_URI_PATH**|**OpenID Connect Redirect URI**: このエンドポイントは Slack の OAuth 許可確認画面からの callback リクエストを処理します。このパスは **SLACK_REDIRECT_URI** の値と整合している必要があります。|

### コード例

どのようにエンドユーザーの OpenID Connect のフローをハンドリングするかを知るには、[Servlet アプリの例](https://github.com/slackapi/java-slack-sdk/blob/main/bolt-servlet/src/test/java/samples/OpenIDConnectSample.java)を参考にしてみてください。

```java
import java.util.*;

// implementation 'com.slack.api:bolt-jetty:{the latest version}'
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

// id_token の JWT の値をでコードしたい場合は、以下の外部ライブラリを使う：
// implementation 'com.auth0:java-jwt:{the latest version}'
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

// 以下の環境変数が設定されていることが前提：
// SLACK_CLIENT_ID, SLACK_CLIENT_SECRET, SLACK_REDIRECT_URI, SLACK_USER_SCOPES
App app = new App().asOpenIDConnectApp(true);

// このコールバック関数で OpenID Connect の code authorization フローをハンドリングできる
app.openIDConnectSuccess((req, resp, token) -> {
  var logger = req.getContext().getLogger();
  
  // TODO: 渡された "token" レスポンス (openid.connect.token API 応答) を保存

  // openid.connect.token レスポンスの id_token をデコード
  DecodedJWT decoded = JWT.decode(token.getIdToken());
  Map<String, Claim> claims = decoded.getClaims();
  logger.info("claims: {}", claims);

  var teamId = claims.get("https://slack.com/team_id").asString();

  // 取得したアクセストークンで openid.connect.userInfo API を呼び出す実装例
  var client = Slack.getInstance().methods();
  try {
    var userInfo = client.openIDConnectUserInfo(r -> r.token(token.getAccessToken()));
    logger.info("userInfo: {}", userInfo);

  } catch (Exception e) {
    throw new RuntimeException(e);
  }

  // エンドユーザーにWebページを表示（または、他のサービスとの OAuth フローに続けるなどどこかにリダイレクトしてもよい）
  var html = app.config().getOAuthRedirectUriPageRenderer().renderSuccessPage(
    null, req.getContext().getOauthCompletionUrl());
  resp.setBody(html);
  resp.setContentType("text/html; charset=utf-8");
  return resp;
});

Map<String, App> apps = new HashMap<>();
apps.put("/slack/", app);
SlackAppServer server = new SlackAppServer(apps);
server.start();
```

もし、[トークンローテーション（英語）](/authentication/using-token-rotation)の機能も同時に有効にする場合、コードは以下のようになるでしょう：

```java
// このコールバック関数で OpenID Connect の code authorization フローをハンドリングできる
app.openIDConnectSuccess((req, resp, token) -> {
  var logger = req.getContext().getLogger();

  // TODO: 渡された "token" レスポンス (openid.connect.token API 応答) を保存

  // openid.connect.token レスポンスの id_token をデコード  
  DecodedJWT decoded = JWT.decode(token.getIdToken());
  Map<String, Claim> claims = decoded.getClaims();
  logger.info("claims: {}", claims);

  var teamId = claims.get("https://slack.com/team_id").asString();

  // 取得したアクセストークンで openid.connect.userInfo API を呼び出す実装例
  var client = Slack.getInstance().methods();
  try {
    if (token.getRefreshToken() != null) {
      // はじめてのトークンローテーションをするコード例
      var refreshedToken = client.openIDConnectToken(r -> r
          .clientId(config.getClientId())
          .clientSecret(config.getClientSecret())
          .grantType("refresh_token")
          .refreshToken(token.getRefreshToken())
      );

      var teamIdWiredClient = Slack.getInstance().methods(refreshedToken.getAccessToken(), teamId);
      var userInfo = teamIdWiredClient.openIDConnectUserInfo(r -> r.token(refreshedToken.getAccessToken()));
      logger.info("userInfo: {}", userInfo);

    } else {
      throw new RuntimeException("Unexpectedly refresh token is absent");
    }

  } catch (Exception e) {
    throw new RuntimeException(e);
  }

  // エンドユーザーにWebページを表示（または、他のサービスとの OAuth フローに続けるなどどこかにリダイレクトしてもよい）
  var html = app.config().getOAuthRedirectUriPageRenderer().renderSuccessPage(
    null, req.getContext().getOauthCompletionUrl());
  resp.setBody(html);
  resp.setContentType("text/html; charset=utf-8");
  return resp;
});
```