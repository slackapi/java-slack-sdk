---
lang: en
---

# Sign in with Slack (OpenID Connect)

[Sign in with Slack](https://api.slack.com/authentication/sign-in-with-slack) helps users log into your service using their Slack profile. The platform feature was recently upgraded to be compatible with the standard [OpenID Connect](https://openid.net/connect/) specification. With Bolt for Java v1.10 or higher, implementing the auth flow is much easier.

## Slack App Configuration

When you create a new Slack app, set the following user scopes:

```yaml
oauth_config:
  redirect_urls:
    - https://example.com/replace-this-with-your-own-redirect-uri
  scopes:
    user:
      - openid   # required
      - email    # optional
      - profile  # optional
```


## Slack Config for Your OpenID Connect App

Here is the list of the necessary configurations for serving your OpenID Connect app built with Bolt. If you prefer using other env variable names or other solutions to load this information, implement your own way to load **AppConfig** instead.

|Env Variable Name|Description (Where to find the value)|
|-|-|
|**SLACK_CLIENT_ID**|**Client ID** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_SECRET**|**Client Secret** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_REDIRECT_URI**|**Redirect URI** (Configure at **Features** > **OAuth & Permissions** > **Redirect URLs**)|
|**SLACK_USER_SCOPES**|**Command-separated list of user scopes**: `scope` parameter that will be appended to `https://slack.com/openid/connect/authorize` as a query parameter. The possible values are `openid`, `email`, and `profile`|
|**SLACK_INSTALL_PATH**|**Starting point of OpenID Connect flow**: This endpoint redirects users to the Slack OpenID Connect endpoint with required query parameters such as `client_id`, `scope`, `state`, and `nonce` (optional).|
|**SLACK_REDIRECT_URI_PATH**|**Path for OpenID Connect Redirect URI**: This endpoint handles callback requests after the Slack's OpenID Connect confirmation. The path must be consistent with **SLACK_REDIRECT_URI** value.|

## Examples

Check [the Servlet app example](https://github.com/slackapi/java-slack-sdk/blob/main/bolt-servlet/src/test/java/samples/OpenIDConnectSample.java) to learn how to implement your Web app that handles the OpenID Connect flow with end-users. 

```java
import java.util.*;

// implementation 'com.slack.api:bolt-jetty:{the latest version}'
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

// If you want to decode the JWT id_token, you can use the following external library for it:
// implementation 'com.auth0:java-jwt:{the latest version}'
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

// The following env variables are supposed to be set:
// SLACK_CLIENT_ID, SLACK_CLIENT_SECRET, SLACK_REDIRECT_URI, SLACK_USER_SCOPES
App app = new App().asOpenIDConnectApp(true);

// You can handle the OpenID Connect code authorization flow with this callback function
app.openIDConnectSuccess((req, resp, token) -> {
  var logger = req.getContext().getLogger();
  
  // TODO: Store the given "token" response (openid.connect.token API response)

  // Decode id_token in an openid.connect.token response
  DecodedJWT decoded = JWT.decode(token.getIdToken());
  Map<String, Claim> claims = decoded.getClaims();
  logger.info("claims: {}", claims);

  var teamId = claims.get("https://slack.com/team_id").asString();

  // Code example demonstrating how to call openid.connect.userInfo using the given access token
  var client = Slack.getInstance().methods();
  try {
    var userInfo = client.openIDConnectUserInfo(r -> r.token(token.getAccessToken()));
    logger.info("userInfo: {}", userInfo);

  } catch (Exception e) {
    throw new RuntimeException(e);
  }

  // Render a web page for the user (or you can redirect the user to the next step such as OAuth with other services)
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

If you enable [the token rotation](https://api.slack.com/authentication/rotation) along with the OpenID Connect, the code can be like this:

```java
// You can handle the OpenID Connect code authorization flow with this callback function
app.openIDConnectSuccess((req, resp, token) -> {
  var logger = req.getContext().getLogger();

  // TODO: Store the given "token" response (openid.connect.token API response)

  // Decode id_token in an openid.connect.token response
  DecodedJWT decoded = JWT.decode(token.getIdToken());
  Map<String, Claim> claims = decoded.getClaims();
  logger.info("claims: {}", claims);

  var teamId = claims.get("https://slack.com/team_id").asString();

  // Code example demonstrating how to call openid.connect.userInfo using the given access token
  var client = Slack.getInstance().methods();
  try {
    if (token.getRefreshToken() != null) {
      // run the first token rotation
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

  // Render a web page for the user (or you can redirect the user to the next step such as OAuth with other services)
  var html = app.config().getOAuthRedirectUriPageRenderer().renderSuccessPage(
    null, req.getContext().getOauthCompletionUrl());
  resp.setBody(html);
  resp.setContentType("text/html; charset=utf-8");
  return resp;
});
```