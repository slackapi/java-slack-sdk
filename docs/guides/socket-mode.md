---
layout: default
title: "Socket Mode"
lang: en
---

# Socket Mode

With [Socket Mode](https://api.slack.com/apis/connections/socket), instead of creating a server with endpoints that Slack sends payloads to, the app will instead connect to Slack via a WebSocket connection and receive data from Slack over the socket connection. In this SDK, **bolt-socket-mode**, a Bolt framework extension for building Socket Mode enabled apps, is available since version 1.5.

### Slack App Configuration

To enable Socket Mode, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Settings** on the left pane. There are a few things to do on the page.

* Go to **Settings** > **Basic Information**
  * Add a new **App-Level Token** with the [`connections:write`](https://api.slack.com/scopes/connections:write) scope
  * Get the generated token value that starts with `xapp-`
* Go to **Settings** > **Socket Mode**
  * Turn on **Enable Socket Mode**
* Configure the features (without setting Request URLs)
* Install the app to receive bot/user tokens (bot: `xoxb-`, user: `xoxp-`)

### Project Setup

To manage the Socket Mode connections, in addition to the **bolt-socket-mode** library, **javax.websocket-api** and **tyrus-standalone-client (1.x)** are required. Here is a minimum Maven settings file. Although we recommend using the Bolt framework for building interactive Slack apps, you can use only the underlying Socket Mode client in **slack-api-client** library along with the WebSocket libraries.

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
      <artifactId>bolt-socket-mode</artifactId>
      <version>{{ site.sdkLatestVersion }}</version>
    </dependency>
    <dependency>
      <groupId>javax.websocket</groupId>
      <artifactId>javax.websocket-api</artifactId>
      <version>1.1</version>
    </dependency>
    <dependency>
      <groupId>org.glassfish.tyrus.bundles</groupId>
      <artifactId>tyrus-standalone-client</artifactId>
      <version>1.17</version>
    </dependency>
  </dependencies>
</project>
```

`SocketModeClient`, the Socket Mode connection manager interface, supports the following open-source libraries for WebSocket communications. If you have suggestions for other libraries that this SDK should support, let us know at [the project issue tracker](https://github.com/slackapi/java-slack-sdk/issues).

|Name|Maven Artifact|
|-|-|
|[Tyrus Standalone Client (default)](https://github.com/eclipse-ee4j/tyrus)|[org.glassfish.tyrus.bundles:tyrus-standalone-client](https://search.maven.org/artifact/org.glassfish.tyrus.bundles/tyrus-standalone-client)|
|[Java-WebSocket](https://github.com/TooTallNate/Java-WebSocket)|[org.java-websocket:Java-WebSocket](https://search.maven.org/artifact/org.java-websocket/Java-WebSocket)|

To switch the underlying implementation, you can pass a `SocketModeClient.Backend.*` to either `SocketModeClient` in **slack-api-client** or `SocketModeApp` in **bolt-socket-mode**. If your own one implements `SocketModeClient` interface, you can just instantiate the class.

```java
Stirng appToken = "xapp-";
App app = new App();
SocketModeApp socketModeApp = new SocketModeApp(
  appToken,
  SocketModeClient.Backend.JavaWebSocket,
  app
);
socketModeApp.start();
```

Needless to say, don't forget adding the required WebSocket library as well. In the case of the above example, you need to add ``org.java-websocket:Java-WebSocket`` in addition to this SDK libraries.

---
## Examples

Even with Socket Mode, the way to initialize an `App` instance and register listeners to it is exactly the same with the HTTP endpoint mode.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;

// The bot token that starts with xoxb- is NOT the app-level token.
// The token here is the one you got by installing the app into a workspace
String botToken = System.getenv("SLACK_BOT_TOKEN");
AppConfig appConfig = AppConfig.builder().singleTeamBotToken(botToken).build();
// If you go with the default constructor, the App initialization requires an env variable named SLACK_BOT_TOKEN.
App app = new App(appConfig);

app.event(AppMentionEvent.class, (req, ctx) -> {
  ctx.say("Hi there!");
  return ctx.ack();
});
```

The `SocketModeApp` class in **bolt-socket-mode** library is an adapter module for Socket Mode communications.

```java
import com.slack.api.bolt.socket_mode.SocketModeApp;

// the app-level token with `connections:write` scope
String appToken = System.getenv("SLACK_APP_TOKEN");

// Initialize the adapter for Socket Mode 
// with an app-level token and your Bolt app with listeners.
SocketModeApp socketModeApp = new SocketModeApp(appToken, app);

// #start() method establishes a new WebSocket connection and then blocks the current thread.
// If you do not want to block this thread, use #startAsync() instead.        
socketModeApp.start();
```

If your app is a distributed app, you can run a Web application for OAuth flow along with Socket Mode this way. In the following example, a single Java process manages both Socket Mode connections and the Web app for OAuth flow. If you want to separate them, it is also possible to do so as long as the two apps use the same `InstallationStore` to store/lookup installation data.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;

// As this is a distributed Socket Mode app, 
// you do not need a token for a specific workspace and the signing secret here.
AppConfig appConfig = AppConfig.builder()
  .clientId("111.222")
  .clientSecret("xxx")
  .scope("app_mentions:read,chat:write,commands")
  .oauthInstallPath("install")
  .oauthRedirectUriPath("oauth_redirect")
  .build();

// Initialize the App and register listeners
App app = new App(appConfig);
app.event(AppMentionEvent.class, (req, ctx) -> {
  ctx.say("Hi there!");
  return ctx.ack();
});

// ------------------------------
// Start a new thread that runs the Socket Mode app in this process
import com.slack.api.bolt.socket_mode.SocketModeApp;

String appToken = "xapp-1-A111-111-xxx";
SocketModeApp socketModeApp = new SocketModeApp(appToken, app);

// This does not block the current thread
socketModeApp.startAsync();

// ------------------------------
// Start an embedded Jetty Web server in this process
import com.slack.api.bolt.jetty.SlackAppServer;
import java.util.HashMap;
import java.util.Map;

Map<String, App> apps = new HashMap<>();
apps.put("/slack/", new App(appConfig).asOAuthApp(true));
SlackAppServer oauthSever = new SlackAppServer(apps);

// Block the current thread
oauthSever.start();

// Access the OAuth URL - https://{your public domain}/slack/install
```

### Under the Hood

If you hope to understand what is actually happening with the above code, running only `SocketModeClient` and checking its debug level logs may be helpful for it. As this is an example that uses the primitive module, you need to check an envelope's type and parse its payload.

```java
import com.slack.api.Slack;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.SocketModeResponse;

String appLevelToken = "xapp-A111-222-xxx";

// Issue a new WSS URL and set the value to the client
try (SocketModeClient client = Slack.getInstance().socketMode(appLevelToken)) {
  // SocketModeClient has #close() method

  // Add a listener function to handle all raw WebSocket text messages
  // You can handle not only envelopes but also any others such as "hello" messages.
  client.addWebSocketMessageListener((String message) -> {
    // TODO: Do something with the raw WebSocket text message
  });
  
  client.addWebSocketErrorListener((Throwable reason) -> {
    // TODO: Do something with a thrown exception
  });
  
  // Add a listener function that handles only type: events envelopes
  client.addEventsApiEnvelopeListener((EventsApiEnvelope envelope) -> {
    // TODO: Do something with an Events API payload

    // Acknowledge the request (within 3 seconds)
    SocketModeResponse ack = AckResponse.builder().envelopeId(envelope.getEnvelopeId()).build();
    client.sendSocketModeResponse(ack);
  });
  
  client.connect(); // Start receiving messages from the Socket Mode server
  
  client.disconnect(); // Disconnect from the server

  client.connectToNewEndpoint(); // Issue a new WSS URL and connects to the URL
}
```