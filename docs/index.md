---
layout: default
title: "Overview"
---

# Slack SDK for Java

**Slack SDK for Java** supports the Slack platform in a Java idiomatic way. The SDK written in Java so developers can use it in any JVM language including Kotlin, Groovy, and Scala.

Within the SDK, there are two different modules:

* [**Bolt for Java**]({{ site.url | append: site.baseurl }}/guides/getting-started-with-bolt), which is a framework with a simple API that makes it easy to write modern Slack apps in Java.
* [**Slack API Client**]({{ site.url | append: site.baseurl }}/guides/web-api-basics), for when you need a more customized approach to building a Slack app in Java.

---

## Modules

The table below shows all the available modules in the Slack Java SDK. All of them have the same latest version as we release all at the same time, even in the case that some don't have any changes apart from updates on their dependency side.

All released versions are available on the Maven Central repositories. The latest version is **{{ site.sdkLatestVersion }}**.

#### Bolt & Built-in Extensions

|groupId:artifactId|Description|
|---|---|
|[**com.slack.api:bolt**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt/{{ site.sdkLatestVersion }}/bolt-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|Bolt is a framework that offers an abstraction layer to build Slack apps safely and quickly. The most commonly used Servlet environment is supported out-of-the-box.|
|[**com.slack.api:bolt-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jetty) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jetty/{{ site.sdkLatestVersion }}/bolt-jetty-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This module offers a handy way to run Bolt apps on the [Jetty HTTP server](https://www.eclipse.org/jetty/).|
|[**com.slack.api:bolt-aws-lambda**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-aws-lambda) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-aws-lambda/{{ site.sdkLatestVersion }}/bolt-aws-lambda-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This module offers a handy way to run Bolt apps on AWS [API Gateway](https://aws.amazon.com/api-gateway/) + [Lambda](https://aws.amazon.com/lambda/).|
|[**com.slack.api:bolt-google-cloud-functions**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-google-cloud-functions) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-google-cloud-functions/{{ site.sdkLatestVersion }}/bolt-google-cloud-functions-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This module offers a handy way to run Bolt apps on [Google Cloud Functions](https://cloud.google.com/functions).|
|[**com.slack.api:bolt-micronaut**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-micronaut) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-micronaut/{{ site.sdkLatestVersion }}/bolt-micronaut-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is an adapter for [Micronaut](https://micronaut.io/) to run Bolt apps on top of it.|
|[**com.slack.api:bolt-helidon**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-helidon) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-helidon/{{ site.sdkLatestVersion }}/bolt-helidon-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is an adapter for [Helidon SE](https://helidon.io/docs/latest/) to run Bolt apps on top of it.|
|[**com.slack.api:bolt-http4k**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-http4k) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-http4k/{{ site.sdkLatestVersion }}/bolt-http4k-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is an adapter for [http4k](https://http4k.org/) to run Bolt apps on top of any of the multiple server backends that the library supports.|
|[**com.slack.api:bolt-ktor**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-ktor) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-ktor/{{ site.sdkLatestVersion }}/bolt-ktor-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is an adapter for [Ktor](https://ktor.io/) to run Bolt apps on top of it.|

#### Foundation Modules

|groupId:artifactId|Description|
|---|---|
|[**com.slack.api:slack-api-model**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model/{{ site.sdkLatestVersion }}/slack-api-model-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is a collection of the classes representing the [Slack core objects](https://api.slack.com/types) such as conversations, messages, users, blocks, and surfaces. As this is an essential part of the SDK, all other modules depend on this.|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-model-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This contains the Block Kit Kotlin DSL builder, which allows you to define block kit structures via a Kotlin-native DSL.|
|[**com.slack.api:slack-api-client**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This is a collection of the Slack API clients. The supported are Basic API Methods, RTM (Real Time Messaging) API, SCIM API, Audit Logs API, and Status API.|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-client-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This contains extension methods for various slack client message builders so you can seamlessly use the Block Kit Kotlin DSL directly on the Java message builders.|
|[**com.slack.api:slack-app-backend**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-app-backend) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-app-backend/{{ site.sdkLatestVersion }}/slack-app-backend-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|This module is a set of Slack app server-side handlers and data classes for Events API, Interactive Components, Slash Commands, Actions, and OAuth flow. These are used by Bolt framework as the foundation of it in primitive layers.|

---

## Requirements

The SDK supports **OpenJDK 8 and higher LTS versions**.

Users can expect every single patch release has been done after verifying functionality by running [the basic CI builds with all LTS versions](https://github.com/slackapi/java-slack-sdk/blob/master/.travis.yml) and all the unit tests have passed at least on the latest LTS version. We don't run comprehensive verifications with all OpenJDK distributions but it should be working with all of them.

---

## Getting Help

If you get stuck, we’re here to help. The following are the best ways to get assistance working through your issue:

* Use our [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) for reporting bugs or requesting features
* Visit the [Slack Developer Community](https://slackcommunity.com/) for getting help using **Slack SDK for Java** or just generally bond with your fellow Slack developers.
