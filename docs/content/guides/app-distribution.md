---
lang: en
---

# App Distribution (OAuth)

A newly created Slack app can only be installed in its development workspace in the beginning. By setting an OAuth Redirect URL and enabling [App Distribution](https://docs.slack.dev/distribution/), the app becomes to be ready for installation in other workspaces.

* [Installing with OAuth](https://docs.slack.dev/authentication/installing-with-oauth)
* [Distributing Slack apps](https://docs.slack.dev/distribution/)

### Slack app configuration

To enable App Distribution, visit the [Slack app settings page](http://api.slack.com/apps), choose the app you're working on, go to **Settings** > **Manage Distribution** on the left pane, and follow the instructions there.

For **Redirect URL**, Bolt apps respond to `https://{your app's public URL domain}/slack/oauth/callback` if you go with recommended settings. To know how to configure such settings, consult the list of the available env variables below in this page.

Bolt for Java automatically includes support for [org-wide installations](https://docs.slack.dev/enterprise-grid/) since version `1.4.0`. Org-wide installations can be enabled in your app settings under **Org Level Apps**.

### What your Bolt app does

To properly handle the OAuth flow:

* Provide an endpoint starting the OAuth flow by redirecting installers to the `authorize` endpoint with sufficient parameters
  * Generate a `state` parameter value to verify afterwards
  * Append `client_id`, `scope`, `user_scope` (only for v2), and `state` to the URL
* Provide an endpoint to handle user redirection from Slack
  * Make sure if the `state` parameter is valid
  * Complete the installation by calling the [`oauth.v2.access`](https://docs.slack.dev/reference/methods/oauth.v2.access) method (or the [`oauth.access`](https://docs.slack.dev/reference/methods/oauth.access) method if you maintain legacy OAuth apps) method and store the acquired tokens
* Provide the endpoints to navigate installers for the completion/cancellation of the installation flow
  * The URLs are usually somewhere else but Bolt has simple functionality to serve them

---
## Examples

Here is a Bolt app demonstrating how to implement an OAuth flow. As the OAuth flow handling features are unnecessary for many custom apps, those are disabled by default. `App` instances need to explicitly call the `asOAuthApp(true)` method to turn them on.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

// API Request Handler App
//  expected env variables:
//   SLACK_SIGNING_SECRET
App apiApp = new App();
apiApp.command("/hi", (req, ctx) -> {
  return ctx.ack("Hi there!");
});

// OAuth Flow Handler App
//  expected env variables:
//   SLACK_CLIENT_ID, SLACK_CLIENT_SECRET, SLACK_REDIRECT_URI, SLACK_SCOPES,
//   SLACK_INSTALL_PATH, SLACK_REDIRECT_URI_PATH
//   SLACK_OAUTH_COMPLETION_URL, SLACK_OAUTH_CANCELLATION_URL
App oauthApp = new App().asOAuthApp(true);

// Mount the two apps with their root path
SlackAppServer server = new SlackAppServer(new HashMap<>(Map.ofEntries(
  entry("/slack/events", apiApp), // POST /slack/events (incoming API requests from the Slack Platform)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (user access)
)));

server.start(); // http://localhost:3000
```

Technically, it's possible to use a single `App` for both Slack API requests and direct user interactions for the OAuth flow, but most apps will prefer to have a different root path for OAuth interactions.

### Slack config for distributing your Slack app

Here is the list of the necessary configurations for distributing apps built with Bolt. If you prefer using other env variable names or other solutions to load this information, implement your own way to load **AppConfig** instead.

|Env Variable Name|Description (Where to find the value)|
|-|-|
|`SLACK_SIGNING_SECRET`|A secret key for verifying requests from Slack. (Find at **Settings** > **Basic Information** > **App Credentials**)|
|`SLACK_CLIENT_ID`|OAuth 2.0 Client ID (Find at **Settings** > **Basic Information** > **App Credentials**)|
|`SLACK_CLIENT_SECRET`|OAuth 2.0 Client Secret (Find at **Settings** > **Basic Information** > **App Credentials**)|
|`SLACK_REDIRECT_URI`|OAuth 2.0 Redirect URI (Configure at **Features** > **OAuth & Permissions** > **Redirect URLs**)|
|`SLACK_SCOPES`|Comma-separated list of scopes: `scope` parameter that will be appended to `https://slack.com/oauth/authorize` and `https://slack.com/oauth/v2/authorize` as a query parameter (Find at **Settings** > **Manage Distribution** > **Sharable URL**, extract the value for `scope`)|
|`SLACK_USER_SCOPES` (only for v2)|Comma-separated list of user scopes: `user_scope` parameter that will be appended to `https://slack.com/oauth/v2/authorize` as a query parameter (Find at **Settings** > **Manage Distribution** > **Sharable URL**, extract the value for `user_scope`)|
|`SLACK_INSTALL_PATH`|Starting point of OAuth flow; this endpoint redirects users to the Slack `authorize` endpoint with required query parameters such as `client_id`, `scope`, `user_scope` (only for v2), and `state`. The suggested path is `/slack/oauth/start`, but you can use any path. Note that the example above automatically prepends `/slack/oauth` to this variable.|
|`SLACK_REDIRECT_URI_PATH`|Path for OAuth Redirect URI; this endpoint handles callback requests after the Slack OAuth confirmation. The path must be consistent with the `SLACK_REDIRECT_URI` value. The suggested path is `/slack/oauth/callback`, but you can use any path. Note that the example above automatically prepends `/slack/oauth` to this variable.|
|`SLACK_OAUTH_COMPLETION_URL`|Installation Completion URL; the complete public URL to redirect users to when their installations have been successfully completed. You can use any URL.|
|`SLACK_OAUTH_CANCELLATION_URL`|Installation Cancellation/Error URL; the complete public URL to redirect users to when their installations have been cancelled. You can use any URL.|

### Choose Proper Storage Services

By default, OAuth flow-supported Bolt apps uses the local file system to generate and store state parameters, and store bot and user tokens. Bolt supports the following out-of-the-box.

* Local File System
* Amazon S3
* Relational database (via JDBC) - [_coming soon!_](https://github.com/slackapi/java-slack-sdk/issues/347)

If your datastore is unsupported, you can implement the interfaces `com.slack.api.bolt.service.InstallationService` and `com.slack.api.bolt.service.OAuthStateService` on your own.

Here is an example app demonstrating how to enable Amazon S3 backed services.

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

// The standard AWS env variables are expected
// export AWS_REGION=us-east-1
// export AWS_ACCESS_KEY_ID=AAAA*************
// export AWS_SECRET_ACCESS_KEY=4o7***********************

// Please be careful about the security policies on this bucket.
String awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE";

InstallationService installationService = new AmazonS3InstallationService(awsS3BucketName);
// Set true if you'd like to store every single installation as a different record
installationService.setHistoricalDataEnabled(true);

// apiApp uses only InstallationService to access stored tokens
App apiApp = new App();
apiApp.command("/hi", (req, ctx) -> {
  return ctx.ack("Hi there!");
});
apiApp.service(installationService);

// Needless to say, oauthApp uses InstallationService
// In addition, it uses OAuthStateService to create/read/delete state parameters
App oauthApp = new App().asOAuthApp(true);
oauthApp.service(installationService);

// Store valid state parameter values in Amazon S3 storage
OAuthStateService stateService = new AmazonS3OAuthStateService(awsS3BucketName);
// This service is necessary only for OAuth flow apps
oauthApp.service(stateService);

// Mount the two apps with their root path
SlackAppServer server = new SlackAppServer(new HashMap<>(Map.ofEntries(
  entry("/slack/events", apiApp), // POST /slack/events (incoming API requests from the Slack Platform)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (user access)
)));

server.start(); // http://localhost:3000
```

If you want to turn the [token rotation feature](https://docs.slack.dev/authentication/using-token-rotation) on, your `InstallationService` should be compatible with it. Refer to the [v1.9.0 release notes](https://github.com/slackapi/java-slack-sdk/releases/tag/v1.9.0) for more details.

### Granular permission apps or classic apps

Slack has two types of OAuth flows for Slack app installations. The V2 (this is a bit confusing because it's not the version of OAuth spec, rather the version of the Slack OAuth flow) OAuth flow enables Slack apps to request more granular permissions than the classic ones, especially for bot users. The differences between the two types are having `v2` in the endpoint to issue access tokens and the OAuth authorization URL, plus some changes to the response data structure returned by the `oauth(.v2).access` endpoint.

#### [V2 OAuth 2.0 Flow](https://docs.slack.dev/authentication/installing-with-oauth) (default)

|Variable|Description|
|--------|-----------|
|Authorization URL|`https://slack.com/oauth/v2/authorize`|
|Web API to issue access tokens|[`oauth.v2.access`](https://docs.slack.dev/reference/methods/oauth.v2.access) ([Response](https://github.com/slackapi/java-slack-sdk/blob/main/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthV2AccessResponse.java))|

#### [Classic OAuth Flow](https://docs.slack.dev/authentication/installing-with-oauth)

|Variable|Description|
|--------|-----------|
|Authorization URL|`https://slack.com/oauth/authorize`|
|Web API to issue access tokens|[`oauth.access`](https://docs.slack.dev/reference/methods/oauth.access) ([Response](https://github.com/slackapi/java-slack-sdk/blob/main/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthAccessResponse.java))|

By default, Bolt enables the V2 OAuth Flow. It's configurable by the `setClassicAppPermissionsEnabled` method. The value is set to `false` by default. Change the flag to `true` to authorize your classic OAuth apps.

```java
AppConfig appConfig = new AppConfig();
appConfig.setClassicAppPermissionsEnabled(true);
App app = new App(appConfig);
```

`InstallationService` absorbs the difference in the response structure, so you don't need to change anything when you switch from the classic OAuth to the V2.

#### Build Slack OAuth using Spring Boot

To implement a Slack OAuth flow:
1) Load `env` variables
2) Initialize `App` with services and listeners, such as a Spring Bean
3) Have three endpoints to handle HTTP requests

:::tip[Tip]
Bolt properly works with Spring Boot 2.2 or newer versions.
:::

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
    App app = new App().asOAuthApp(true); // Do not forget calling `asOAuthApp(true)` here
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

If you want to use different implementations of `InstallationService` and `OAuthStateService`, you can have them as Spring components this way:

```java
public class SlackApp {
  // Please be careful about the security policies on this bucket.
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

#### Use the Built-in `tokens_revoked` / `app_uninstalled` event handlers

For secure data management for your customers and end users, properly handling [`tokens_revoked`](https://docs.slack.dev/reference/events/tokens_revoked) and [`app_uninstalled`](https://docs.slack.dev/reference/events/app_uninstalled) events is crucial. Bolt for Java provides the built-in event handlers for these events, which seamlessly integrate with your `InstallationService`'s deletion methods.

```java
App app = new App();
InstallationService installationService = new MyInstallationService();
app.service(installationService);
// Turn the event handlers on
app.enableTokenRevocationHandlers();
```

The above code is equivalent to the following:

```java
App app = new App();
InstallationService installationService = new MyInstallationService();
app.service(installationService);
// Turn the event handlers on
app.event(TokensRevokedEvent.class, app.defaultTokensRevokedEventHandler());
app.event(AppUninstalledEvent.class, app.defaultAppUninstalledEventHandler());
```

To enable your own custom `InstallationService` classes to work with the built-in event handlers, the classes need to implement the following methods in the [`InstallationService`](https://github.com/seratch/java-slack-sdk/blob/main/bolt/src/main/java/com/slack/api/bolt/service/InstallationService.java) interface:

* `void deleteBot(Bot bot)`
* `void deleteInstaller(Installer installer)`
* `void deleteAll(String enterpriseId, String teamId)`

#### Serve completion/cancellation pages in Bolt apps

Although most apps tend to choose static pages for the completion/cancellation URLs, it's also possible to dynamically serve those URLs in the same app. Bolt does not offer any features to render web pages. Use your favorite template engine for it.

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
