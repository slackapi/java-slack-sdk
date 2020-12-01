---
layout: default
title: "App Distribution (OAuth)"
lang: en
---

# App Distribution (OAuth)

A newly created Slack app can only be installed in its development workspace in the beginning. By setting an OAuth Redirect URL and enabling [App Distribution](https://api.slack.com/start/distributing), the app becomes to be ready for installation in any other workspaces.

* [Using OAuth 2.0](https://api.slack.com/docs/oauth)
* [Distributing Slack Apps](https://api.slack.com/start/distributing)

### Slack App Configuration

To enable App Distribution, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, go to **Settings** > **Manage Distribution** on the left pane, and follow the instructions there.

For **Redirect URL**, Bolt apps respond to `https://{your app's public URL domain}/slack/oauth/callback` if you go with recommended settings. To know how to configure such settings, consult the list of the available env variables below in this page.

Bolt for Java automatically includes support for [org wide installations](https://api.slack.com/enterprise/apps) since version `1.4.0`. Org wide installations can be enabled in your app configuration settings under **Org Level Apps**.

### What Your Bolt App Does

All your app needs to do to properly handle OAuth Flow are:

* Provide an endpoint starting OAuth flow by redirecting installers to Slack's Authorize endpoint with sufficient parameters
  * Generate a `state` parameter value to verify afterwards
  * Append `client_id`, `scope`, `user_scope` (only for v2), and `state` to the URL
* Provide an endpoint to handle user redirection from Slack
  * Make sure if the `state` parameter is valid
  * Complete the installation by calling [oauth.v2.access](https://api.slack.com/methods/oauth.v2.access) (or [oauth.access](https://api.slack.com/methods/oauth.access)) method and store the acquired tokens
* Provide the endpoints to navigate installers for the completion/cancellation of the installation flow
  * The URLs are usually somewhere else but Bolt has simple functionality to serve them

---
## Examples

Here is a Bolt app demonstrating how to implement OAuth flow. As the OAuth flow handling features are unnecessary for many custom apps, those are disabled by default. **App** instances need to explicitly call `asOAuthApp(true)` to turn on them.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
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
SlackAppServer server = new SlackAppServer(Map.of(
  entry("/slack/events", apiApp), // POST /slack/events (incoming API requests from the Slack Platform)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (user access)
));

server.start(); // http://localhost:3000
```

Technically, it's possible to use a single **App** for both Slack API requests and direct user interactions for the OAuth flow. But most apps probably will prefer to have a different root path for OAuth interactions.

### Slack Config for Distributing Your Slack App

Here is the list of the necessary configurations for distributing apps built with Bolt. If you prefer using other env variable names or other solutions to load this information, implement your own way to load **SlackConfig** instead.

|Env Variable Name|Description (Where to find the value)|
|-|-|
|**SLACK_SIGNING_SECRET**|**Signing Secret**: A secret key for verifying requests from Slack. (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_ID**|**OAuth 2.0 Client ID** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_CLIENT_SECRET**|**OAuth 2.0 Client Secret** (Find at **Settings** > **Basic Information** > **App Credentials**)|
|**SLACK_REDIRECT_URI**|**OAUth 2.0 Redirect URI** (Configure at **Features** > **OAuth & Permissions** > **Redirect URLs**)|
|**SLACK_SCOPES**|**Command-separated list of scopes**: `scope` parameter that will be appended to `https://slack.com/oauth/authorize` and `https://slack.com/oauth/v2/authorize` as a query parameter (Find at **Settings** > **Manage Distribution** > **Sharable URL**, extract the value for `scope`)|
|**SLACK_USER_SCOPES** (only for v2)|**Command-separated list of user scopes**: `user_scope` parameter that will be appended to `https://slack.com/oauth/v2/authorize` as a query parameter (Find at **Settings** > **Manage Distribution** > **Sharable URL**, extract the value for `user_scope`)|
|**SLACK_INSTALL_PATH**|**Starting point of OAuth flow**: This endpoint redirects users to the Slack Authorize endpoint with required query parameters such as `client_id`, `scope`, `user_scope` (only for v2), and `state`. The suggested path is `/slack/oauth/start` but you can go with any path.|
|**SLACK_REDIRECT_URI_PATH**|**Path for OAuth Redirect URI**: This endpoint handles callback requests after the Slack's OAuth confirmation. The path must be consistent with **SLACK_REDIRECT_URI** value. The suggested path is `/slack/oauth/callback` but you can go with any path.|
|**SLACK_OAUTH_COMPLETION_URL**|**Installation Completion URL**: The complete public URL to redirect users when their installations have been successfully completed. You can go with any URLs.|
|**SLACK_OAUTH_CANCELLATION_URL**|**Installation Cancellation/Error URL**: The complete public URL to redirect users when their installations have been cancelled for some reasons. You can go with any URLs.|

### Choose Proper Storage Services

By default, OAuth flow supported Bolt apps uses the local file system to generate/store state parameters, and store bot/user tokens. Bolt supports the following out-of-the-box.

* Local File System
* Amazon S3
* Relational Database (via JDBC) - [_coming soon!_](https://github.com/slackapi/java-slack-sdk/issues/347)

If your datastore is unsupported, you can implement the interfaces **com.slack.api.bolt.service.InstallationService** and **com.slack.api.bolt.service.OAuthStateService** on your own.

Here is an example app demonstrating how to enable Amazon S3 backed services.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;

import java.util.Map;
import static java.util.Map.entry;

// The standard AWS env variables are expected
// export AWS_REGION=us-east-1
// export AWS_ACCESS_KEY_ID=AAAA*************
// export AWS_SECRET_ACCESS_KEY=4o7***********************

// Please be careful about the security policies on this bucket.
String awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE";

InstallationService installationService = new AmazonS3InstallationService(awsS3BucketName);
// Set true if you'd like to store every single instllation as a different record
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
SlackAppServer server = new SlackAppServer(Map.of(
  entry("/slack/events", apiApp), // POST /slack/events (incomng API requests from the Slack Platform)
  entry("/slack/oauth", oauthApp) // GET  /slack/oauth/start, /slack/oauth/callback (user access)
));

server.start(); // http://localhost:3000
```

### Granular Permission Apps or Classic Apps

Slack has two types of OAuth flows for Slack app installations. The V2 (this is a bit confusing but it's not the version of OAuth spec, but the version of the Slack OAuth flow) OAuth flow enables Slack apps to request more granular permissions than the classic ones, especially for bot users. The differences between the two types are having `v2` in the endpoint to issue access tokens and the OAuth Authorization URL, plus some changes to the response data structure returned by the `oauth(.v2).access` endpoint.

#### [V2 OAuth 2.0 Flow](https://api.slack.com/authentication/oauth-v2) (default)

|-|-|
|Authorization URL|`https://slack.com/oauth/v2/authorize`|
|Web API to issue access tokens|[`oauth.v2.access`](https://api.slack.com/methods/oauth.v2.access) ([Response](https://github.com/slackapi/java-slack-sdk/blob/master/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthV2AccessResponse.java))|

#### [Classic OAuth Flow](https://api.slack.com/docs/oauth)

|-|-|
|Authorization URL|`https://slack.com/oauth/authorize`|
|Web API to issue access tokens|[`oauth.access`](https://api.slack.com/methods/oauth.access) ([Response](https://github.com/slackapi/java-slack-sdk/blob/master/slack-api-client/src/main/java/com/slack/api/methods/response/oauth/OAuthAccessResponse.java))|

By default, Bolt enables the V2 OAuth Flow over the classic one. It's configurable by **AppConfig**'s the setter method for `classicAppPermissionsEnabled`. The value is set to `false` by default. Change the flag to `true` to authorize your classic OAuth apps.

```java
AppConfig appConfig = new AppConfig();
appConfig.setClassicAppPermissionsEnabled(true);
App app = new App(appConfig);
```

**InstallationService** absorbs the difference in the response structure. So, you don't need to change anything even when you switch from the classic OAuth to the V2.


#### Build Slack OAuth using Spring Boot

Implementing Slack OAuth flow app using Spring Boot is quite easy. All you need to do are to 1) load env variables, 2) to initialize `App` with services and listeners as a Spring Bean, and 3) to have three endpoints to handle HTTP requests.

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

#### Serve the Completion/Cancellation Pages in Bolt Apps

Although most apps tend to choose static pages for the completion/cancellation URLs, it's also possible to dynamically serve those URLs in the same app. Bolt doesn't offer any features to render web pages. Use your favorite template engine for it.

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
