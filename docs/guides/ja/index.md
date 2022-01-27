---
layout: ja
title: "SDK の概要"
lang: ja
---

# Slack SDK for Java

**Slack SDK for Java** は Java らしいアプローチで全ての Slack プラットフォームの公開機能をサポートする公式 SDK です。この SDK は全て Java で書かれており、開発者はこの SDK を Java だけでなく Kotlin、Groovy、Scala といった JVM 言語でも利用できます。

この SDK は大きく分けて主に二種類のモジュールを提供しています。

* [**Bolt️ for Java**]({{ site.url | append: site.baseurl }}/guides/ja/bolt-basics) は、最新の Slack アプリをシンプルな API を用いて簡単に開発するためのフレームワークです
* [**Slack API クライアント**]({{ site.url | append: site.baseurl }}/guides/ja/web-api-basics) は、より柔軟に Slack アプリを開発したい場合にこれだけを組み込んで使用することができます

---
## <!--Modules--> モジュール一覧

以下のテーブルは、この Java SDK で現在提供されているモジュールの一覧を示しています。これらのモジュールは、たとえその一部のモジュール自体には変更がなく、依存ライブラリ側の変更しかなかったとしても、すべてのモジュールが必ず同じタイミングでリリースされます。そのため、いかなるタイミングでも、必ず同一の最新バージョンが存在します。

全てのリリースは Maven Central リポジトリで公開されています。最新のバージョンは **{{ site.sdkLatestVersion }}** です。

#### Bolt とその標準拡張モジュール

|groupId:artifactId| <!--Description-->説明                                                                                                                 |
|---|--------------------------------------------------------------------------------------------------------------------------------------|
|[**com.slack.api:bolt**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt/{{ site.sdkLatestVersion }}/bolt-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Bolt は全ての Slack プラットフォームの公開機能を利用して Slack アプリを開発するためのフレームワークで、特定の環境やフレームワークに依存しない抽象化されたレイヤーを提供します。                                    |
|[**com.slack.api:bolt-socket-mode**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-socket-mode) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-socket-mode/{{ site.sdkLatestVersion }}/bolt-socket-mode-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| ソケットモード環境で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                                                       |
|[**com.slack.api:bolt-servlet**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-servlet) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-servlet/{{ site.sdkLatestVersion }}/bolt-servlet-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Java EE Servlet 環境で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                                              |
|[**com.slack.api:bolt-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jetty) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jetty/{{ site.sdkLatestVersion }}/bolt-jetty-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Bolt で実装された Slack アプリを [Java EE 互換 Jetty HTTP サーバー (9.x)](https://www.eclipse.org/jetty/)で動作させるモジュールです。                              |
|[**com.slack.api:bolt-jakarta-servlet**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jakarta-servlet) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jakarta-servlet/{{ site.sdkLatestVersion }}/bolt-jakarta-servlet-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Jakarta EE Servlet 環境で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                                           |
|[**com.slack.api:bolt-jakarta-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jakarta-jetty) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-jakarta-jetty/{{ site.sdkLatestVersion }}/bolt-jakarta-jetty-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Bolt で実装された Slack アプリを [Jakarta EE 互換 Jetty HTTP サーバー](https://www.eclipse.org/jetty/)で動作させるモジュールです。                                 |
|[**com.slack.api:bolt-aws-lambda**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-aws-lambda) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-aws-lambda/{{ site.sdkLatestVersion }}/bolt-aws-lambda-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Bolt で実装された Slack アプリを AWS [API Gateway](https://aws.amazon.com/api-gateway/) + [Lambda](https://aws.amazon.com/lambda/) で動作させるためのモジュールです。 |
|[**com.slack.api:bolt-google-cloud-functions**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-google-cloud-functions) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-google-cloud-functions/{{ site.sdkLatestVersion }}/bolt-google-cloud-functions-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| Bolt で実装された Slack アプリを [Google Cloud Functions](https://cloud.google.com/functions) で動作させるためのモジュールです。                                |
|[**com.slack.api:bolt-micronaut**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-micronaut) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-micronaut/{{ site.sdkLatestVersion }}/bolt-micronaut-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| [Micronaut](https://micronaut.io/) で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                             |
|[**com.slack.api:bolt-helidon**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-helidon) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-helidon/{{ site.sdkLatestVersion }}/bolt-helidon-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| [Helidon SE](https://helidon.io/docs/latest/) で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                  |
|[**com.slack.api:bolt-http4k**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-http4k) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-http4k/{{ site.sdkLatestVersion }}/bolt-http4k-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| [http4k](https://http4k.org/) で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                                  |
|[**com.slack.api:bolt-ktor**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-ktor) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/bolt-ktor/{{ site.sdkLatestVersion }}/bolt-ktor-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)| [Ktor](https://ktor.io/) で Bolt アプリを動作させるためのアダプターを提供するモジュールです。                                                                       |

#### 基盤モジュール

|groupId:artifactId|Description|
|---|---|
|[**com.slack.api:slack-api-model**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model/{{ site.sdkLatestVersion }}/slack-api-model-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|チャンネル、メッセージ、ユーザー、Block Kit のブロックとそれによって構成されるサーフェスエリアなど [Slack の核となるような重要なオブジェクト（英語）](https://api.slack.com/types)を表現するクラス群を提供します。|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-model-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|Block Kit のデータ構造を Kotlin ネイティブな DSL を使って構築できるビルダーのモジュールを提供します。|
|[**com.slack.api:slack-api-client**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client/{{ site.sdkLatestVersion }}/slack-api-client-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|様々な Slack API クライアントを提供します。サポートされているのは、API メソッド、ソケットモード、RTM API、SCIM API、Audit Logs API、ステータス API です。|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-client-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|Slack API クライアントのリクエストビルダーのメソッドを拡張することで、Block Kit のデータ構造を構築するための Kotlin ネイティブな DSL を直接利用できるようにするモジュールを提供します。|
|[**com.slack.api:slack-app-backend**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-app-backend) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-app-backend/{{ site.sdkLatestVersion }}/slack-app-backend-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|Slack アプリサーバーサイドで必要となる共通モジュールやペイロードなどのデータ構造を提供します。サポートされているのは、イベント API、インタラクティブコンポーネント、スラッシュコマンド、アクション、そして OAuth フローです。これらの機能はよりプリミティブなレイヤーとして Bolt から利用されています。|


---
## <!--Requirements--> 動作条件

この SDK は **OpenJDK 8 かそれよりも新しい LTS バージョン** での動作を保証しています。

全てのパッチリリースが[全ての LTS バージョンでの基本的な CI ビルド](https://github.com/slackapi/java-slack-sdk/blob/main/.travis.yml)が実行されており、利用者は最新の LTS バージョンで全てのユニットテストが成功していることを期待することができます。SDK 開発チームは、全ての OpenJDK ディストリビューションでの網羅的な QA テストまでは実施していませんが、問題はまず発生しないはずです。

---
## <!--Getting Help--> ヘルプ

何か困ったことがあったら、以下の場所でサポートを受けてみてください。

* [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) にバグや機能要望を報告する（**必ず英語でお願いします**）
* [Slack Developer Community](https://slackcommunity.com/) で **Slack SDK for Java** の使い方についてヘルプを求めたり、他の開発者たちとつながる
