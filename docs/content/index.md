---
layout: default
---

<!-- import { sdkLatestVersion } from '@site/src/JavaVersion.js'; -->


# Slack Java SDK

The Slack Java SDK supports the Slack platform in a Java idiomatic way. The SDK written in Java so developers can use it in any JVM language including Kotlin, Groovy, and Scala.

Within the SDK, there are two different modules:

* [**Bolt for Java**](/getting-started-with-bolt), which is a framework with a simple API that makes it easy to write modern Slack apps in Java.
* [**Slack API Client**](/web-api-basics), for when you need a more customized approach to building a Slack app in Java.

---

## Requirements

The Slack Java SDK supports **OpenJDK 8 and higher LTS versions**.

Users can expect every single patch release has been done after verifying functionality by running [the basic CI builds with all LTS versions](https://github.com/slackapi/java-slack-sdk/blob/main/.travis.yml) and all the unit tests have passed at least on the latest LTS version. We don't run comprehensive verifications with all OpenJDK distributions but it should be working with all of them.


## Modules

The table below shows all the available modules in the Slack Java SDK. All of them have the same latest version as we release all at the same time, even in the case that some don't have any changes apart from updates on their dependency side.

All released versions are available on the Maven Central repositories. The latest version is **sdkLatestVersion**.

#### Bolt & Built-in Extensions

|groupId:artifactId| Javadoc | Description |
|------------------|---------|-------------|
|[**com.slack.api:bolt**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt/$sdkLatestVersion/bolt-$sdkLatestVersion-javadoc.jar/!/index.html#package`}>Javadoc</a>| Bolt is a framework that offers an abstraction layer to build Slack apps safely and quickly.                                                          |
|[**com.slack.api:bolt-socket-mode**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-socket-mode) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-socket-mode/$sdkLatestVersion/bolt-socket-mode-$sdkLatestVersion-javadoc.jar/!/index.html#package`}>Javadoc</a>| This module offers a handy way to run Bolt apps through [Socket Mode](https://api.slack.com/) connections.                                            |
|[**com.slack.api:bolt-servlet**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-servlet) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-servlet/$sdkLatestVersion/bolt-servlet-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on the Java EE Servlet environments.                                                                  |
|[**com.slack.api:bolt-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jetty) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jetty/$sdkLatestVersion/bolt-jetty-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on the [Java EE compatible Jetty HTTP server (9.x)](https://www.eclipse.org/jetty/).                  |
|[**com.slack.api:bolt-jakarta-servlet**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jakarta-servlet) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jakarta-servlet/$sdkLatestVersion/bolt-jakarta-servlet-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on the Jakarta EE Servlet environments.                                                               |
|[**com.slack.api:bolt-jakarta-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jakarta-jetty)| <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jakarta-jetty/$sdkLatestVersion/bolt-jakarta-jetty-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on the [Jakarta EE compatible Jetty HTTP server](https://www.eclipse.org/jetty/).                     |
|[**com.slack.api:bolt-aws-lambda**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-aws-lambda) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-aws-lambda/$sdkLatestVersion/bolt-aws-lambda-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on AWS [API Gateway](https://aws.amazon.com/api-gateway/) + [Lambda](https://aws.amazon.com/lambda/). |
|[**com.slack.api:bolt-google-cloud-functions**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-google-cloud-functions)| <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-google-cloud-functions/$sdkLatestVersion/bolt-google-cloud-functions-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This module offers a handy way to run Bolt apps on [Google Cloud Functions](https://cloud.google.com/functions).     |
|[**com.slack.api:bolt-micronaut**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-micronaut)| <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-micronaut/$sdkLatestVersion/bolt-micronaut-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This is an adapter for [Micronaut](https://micronaut.io/) to run Bolt apps on top of it.                                                              |
|[**com.slack.api:bolt-helidon**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-helidon) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-helidon/$sdkLatestVersion/bolt-helidon-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This is an adapter for [Helidon SE](https://helidon.io/docs/latest/) to run Bolt apps on top of it.                                                   |
|[**com.slack.api:bolt-http4k**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-http4k) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-http4k/$sdkLatestVersion/bolt-http4k-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This is an adapter for [http4k](https://http4k.org/) to run Bolt apps on top of any of the multiple server backends that the library supports.        |
|[**com.slack.api:bolt-ktor**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-ktor) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-ktor/$sdkLatestVersion/bolt-ktor-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>| This is an adapter for [Ktor](https://ktor.io/) to run Bolt apps on top of it.                                                                        |

#### Foundation Modules

|groupId:artifactId |Javadoc| Description|
|---|---|---|
|[**com.slack.api:slack-api-model**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model/$sdkLatestVersion/slack-api-model-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>|This is a collection of the classes representing the [Slack core objects](https://api.slack.com/types) such as conversations, messages, users, blocks, and surfaces. As this is an essential part of the SDK, all other modules depend on this.|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model-kotlin-extension/$sdkLatestVersion/slack-api-model-kotlin-extension-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>|This contains the Block Kit Kotlin DSL builder, which allows you to define block kit structures via a Kotlin-native DSL.|
|[**com.slack.api:slack-api-client**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/$sdkLatestVersion/slack-api-client-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>|This is a collection of the Slack API clients. The supported are Basic API Methods, Socket Mode API, RTM API, SCIM API, Audit Logs API, and Status API.|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client-kotlin-extension/$sdkLatestVersion/slack-api-client-kotlin-extension-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>|This contains extension methods for various slack client message builders so you can seamlessly use the Block Kit Kotlin DSL directly on the Java message builders.|
|[**com.slack.api:slack-app-backend**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-app-backend) | <a href={`https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-app-backend/$sdkLatestVersion/slack-app-backend-$sdkLatestVersion-javadoc.jar/!/index.html`}>Javadoc</a>|This module is a set of Slack app server-side handlers and data classes for Events API, Interactive Components, Slash Commands, Actions, and OAuth flow. These are used by Bolt framework as the foundation of it in primitive layers.|

## Getting help

These docs have lots of information on the Java Slack SDK. There's also an in-depth Reference section. Please explore!

If you otherwise get stuck, we're here to help. The following are the best ways to get assistance working through your issue:

* Visit the [Issue Tracker](http://github.com/slackapi/java-slack-sdk/issues) for questions, bug reports, feature requests, and general discussion related to Bolt for JavaScript. Try searching for an existing issue before creating a new one.
* Visit the [Slack Developer Community](https://slackcommunity.com/) for getting help or for just bonding with your fellow Slack developers.
* [Email](mailto:support@slack.com) our developer support team: `support@slack.com`.

## Contributing

These docs live within the [Java Slack SDK](https://github.com/slackapi/java-slack-sdk/) repository and are open source.

We welcome contributions from everyone! Please check out our [Contributor's Guide](https://github.com/slackapi/java-slack-sdk/blob/main/.github/contributing.md) for how to contribute in a helpful and collaborative way.
