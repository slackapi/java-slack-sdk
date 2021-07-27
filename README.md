## Slack SDK for Java

[![Maven Central](https://img.shields.io/maven-central/v/com.slack.api/slack-api-client.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.slack.api%22%20a%3A%22slack-api-client%22) [![CI Build](https://github.com/slackapi/java-slack-sdk/workflows/CI%20Build/badge.svg)](https://github.com/slackapi/java-slack-sdk/actions?query=workflow%3A%22CI+Build%22) [![codecov](https://codecov.io/gh/slackapi/java-slack-sdk/branch/main/graph/badge.svg)](https://codecov.io/gh/slackapi/java-slack-sdk)

**Slack SDK for Java** supports the Slack platform in a Java idiomatic way. The SDK written in Java so developers can use it in any JVM language including Kotlin, Groovy, and Scala.

Within the SDK, there are two different modules:

* [**Bolt for Java**](https://slack.dev/java-slack-sdk/guides/getting-started-with-bolt), which is a framework with a simple API that makes it easy to write modern Slack apps in Java.
* [**Slack API Client**](https://slack.dev/java-slack-sdk/guides/web-api-basics), for when you need a more customized approach to building a Slack app in Java.

If what you want to do is call Slack APIs in your existing services, we recommend using only the **Slack API Client**. If instead, you’re developing a new modern and interactive Slack app, we recommend **Bolt** for it. The framework enables developers to focus on the essential parts of their apps without being bothered by trifles.

## Bolt for Java

**Bolt for Java** is a framework on the JVM that offers an abstraction layer to build Slack apps quickly using modern platform features. Refer to [Getting Started with Bolt](https://slack.dev/java-slack-sdk/guides/getting-started-with-bolt) for detailed instructions.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
```

For Socket Mode enabled apps, [Getting Started with Bolt (Socket Mode)](https://slack.dev/java-slack-sdk/guides/getting-started-with-bolt-socket-mode) is available.

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // App expects an env variable: SLACK_BOT_TOKEN
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    // SocketModeApp expects an env variable: SLACK_APP_TOKEN
    new SocketModeApp(app).start();
  }
}
```

## Slack API Client

**slack-api-client** contains simple, easy-to-use, and flexibly configurable HTTP clients for making requests to Slack APIs. Refer to [API Client Basics](https://slack.dev/java-slack-sdk/guides/web-api-basics) for details.

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_TOKEN");

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567") // Channel ID
  .text(":wave: Hi from a bot written in Java!"));
```

## Modules

The table below shows all the available modules in the Slack Java SDK. All of them have the same latest version as we release all at the same time, even in the case that some don't have any changes apart from updates on their dependency side.

All released versions are available on the Maven Central repositories. The latest version is **{{ site.sdkLatestVersion }}**.

#### Bolt & Built-in Extensions

|groupId:artifactId|Description|
|---|---|
|[**com.slack.api:bolt**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt)|Bolt is a framework that offers an abstraction layer to build Slack apps safely and quickly. The most commonly used Servlet environment is supported out-of-the-box.|
|[**com.slack.api:bolt-socket-mode**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-socket-mode)|This module offers a handy way to run Bolt apps through [Socket Mode](https://api.slack.com/) connections.|
|[**com.slack.api:bolt-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jetty)|This module offers a handy way to run Bolt apps on the [Jetty HTTP server](https://www.eclipse.org/jetty/).|
|[**com.slack.api:bolt-aws-lambda**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-aws-lambda)|This module offers a handy way to run Bolt apps on AWS [API Gateway](https://aws.amazon.com/api-gateway/) + [Lambda](https://aws.amazon.com/lambda/).|
|[**com.slack.api:bolt-google-cloud-functions**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-google-cloud-functions)|This module offers a handy way to run Bolt apps on [Google Cloud Functions](https://cloud.google.com/functions).|
|[**com.slack.api:bolt-micronaut**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-micronaut)|This is an adapter for [Micronaut](https://micronaut.io/) to run Bolt apps on top of it.|
|[**com.slack.api:bolt-helidon**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-helidon)|This is an adapter for [Helidon SE](https://helidon.io/docs/latest/) to run Bolt apps on top of it.|
|[**com.slack.api:bolt-http4k**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-http4k)|This is an adapter for [http4k](https://http4k.org/) to run Bolt apps on top of any of the multiple server backends that the library supports.|
|[**com.slack.api:bolt-ktor**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-ktor)|This is an adapter for [Ktor](https://ktor.io/) to run Bolt apps on top of it.|

#### Foundation Modules

|groupId:artifactId|Description|
|---|---|
|[**com.slack.api:slack-api-model**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model)|This is a collection of the classes representing the [Slack core objects](https://api.slack.com/types) such as conversations, messages, users, blocks, and surfaces. As this is an essential part of the SDK, all other modules depend on this.|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension)|This contains the Block Kit Kotlin DSL builder, which allows you to define block kit structures via a Kotlin-native DSL.|
|[**com.slack.api:slack-api-client**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client)|This is a collection of the Slack API clients. The supported are Basic API Methods, RTM (Real Time Messaging) API, SCIM API, Audit Logs API, and Status API.|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension)|This contains extension methods for various slack client message builders so you can seamlessly use the Block Kit Kotlin DSL directly on the Java message builders.|
|[**com.slack.api:slack-app-backend**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-app-backend)|This module is a set of Slack app server-side handlers and data classes for Events API, Interactive Components, Slash Commands, Actions, and OAuth flow. These are used by Bolt framework as the foundation of it in primitive layers.|

## Requirements

The SDK supports **OpenJDK 8 and higher LTS versions**.

Users can expect every single patch release has been done after verifying functionality by running [the basic CI builds with all LTS versions](https://github.com/slackapi/java-slack-sdk/blob/main/.travis.yml) and all the unit tests have passed at least on the latest LTS version. We don't run comprehensive verifications with all OpenJDK distributions but it should be working with all of them.

## Getting Help

If you get stuck, we’re here to help. The following are the best ways to get assistance working through your issue:

* Use our [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) for reporting bugs or requesting features
* Visit the [Slack Developer Community](https://slackcommunity.com/) for getting help using **Slack SDK for Java** or just generally bond with your fellow Slack developers.

## Important Notice for jSlack users

The [jSlack](https://search.maven.org/artifact/com.github.seratch/jslack) project has been transferred to [@slackapi](http://github.com/slackapi). The jSlack maintenance releases for security issues or major bugfixes will be continued at https://github.com/seratch/jslack-maintenance-releases .

### Contributing

We welcome contributions from everyone! Please check out our [Contributor's Guide](.github/contributing.md) for how to contribute in a helpful and collaborative way.
