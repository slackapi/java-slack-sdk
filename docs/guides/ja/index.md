---
layout: ja
title: "SDK の概要"
lang: ja
---

# Slack SDK for Java / Bolt for Java️

**Slack SDK for Java** は、すべての公開された Slack プラットフォーム機能を Java らしい方法でサポートする公式の SDK です。SDK のすべてのコードは Java で書かれており、開発者はこの SDK を Java だけでなく、Kotlin、Groovy、Scala といった JVM 言語でも問題なく利用できます。

この SDK は、プラットフォーム機能群をそれぞれ対応するモジュールとして提供しています。大きく分けて主に二種類のモジュールを提供しています。

* [**Slack API クライアント**]({{ site.url | append: site.baseurl }}/guides/ja/web-api-basics) - 様々な Slack API を呼び出すために利用します
* [**Bolt️**]({{ site.url | append: site.baseurl }}/guides/bolt) - プラットフォームの機能をフル活用した Slack アプリのバックエンドサービスを開発するためのフレームワークです

もしあなたが既存のサービス内で Slack API を呼び出す機能を追加したいという場合であれば、おそらく **Slack API クライアント**だけを利用すればよいでしょう。もしモダンでインタラクティブな Slack アプリを開発したいなら、**Bolt** を利用することをおすすめします。このフレームワークを利用することで、開発者に些末なことに煩わされることなく、Slack アプリの本質的な部分な実装だけに集中できるようになります。

---

## <!--Modules--> モジュール一覧

以下のテーブルは、現在提供されているモジュールの一覧を示しています。これらのモジュールは、たとえその一部のモジュール自体には変更がなく、依存ライブラリ側の変更しかなかったとしても、すべてのモジュールが必ず同じタイミングでリリースされます。そのため、いかなるタイミングでも、必ず同一の最新バージョンが存在します。

全てのリリースは Maven Central リポジトリで公開されています。最新のバージョンは **{{ site.sdkLatestVersion }}** です。

|groupId:artifactId|<!--Description-->説明|
|---|---|
|[**com.slack.api:slack-api-model**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model)|このモジュールはカンバセーション（チャンネルなど）、メッセージ、ユーザー、Block Kit のブロックとそれによって構成されるユーザーインターフェースなど [Slack の核となるような重要なオブジェクト](https://api.slack.com/types)を表現する class 群を提供します。|
|[**com.slack.api:slack-api-client**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client)|このモジュールは様々な Slack API クライアントを提供します。サポートされているのは、API Methods、RTM (Real Time Messaging) API、SCIM API、Audit Logs API、ステータス API です。|
|[**com.slack.api:slack-app-backend**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-app-backend)|このモジュールは Slack アプリサーバサイドで必要となる共通モジュールやペイロードなどのデータ構造を提供します。サポートされているのは、イベント API、インタラクティブコンポーネント、スラッシュコマンド、アクション、そして OAuth フローです。これらの機能はよりプリミティブなレイヤーとして Bolt から利用されています。|
|[**com.slack.api:bolt**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt)|Bolt は Slack アプリをより安全かつ早く作るためのフレームワークで、特定の環境やフレームワークに依存しない抽象化されたレイヤーを提供します。広く利用されている Servlet API へのアダプターは標準でサポートされています。|
|[**com.slack.api:bolt-jetty**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-jetty)|このモジュールは Bolt で実装された Slack アプリを Jetty サーバで動作させるためのモジュールです。|
|[**com.slack.api:bolt-aws-lambda**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-aws-lambda)|このモジュールは Bolt で実装された Slack アプリを AWS [API Gateway](https://aws.amazon.com/api-gateway/) + [Lambda](https://aws.amazon.com/lambda/) で動作させるためのモジュールです。|
|[**com.slack.api:bolt-micronaut**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:bolt-micronaut)|このモジュールは [Micronaut](https://micronaut.io/) というフレームワークを使って Bolt アプリを動作させるためのアダプターを提供するモジュールです。|

---

## <!--Requirements--> 動作条件

この SDK は **OpenJDK 8 かそれよりも新しい LTS バージョン** での動作を保証しています。

利用者は、全てのパッチリリースが[全ての LTS バージョンでの基本的な CI ビルド](https://github.com/slackapi/java-slack-sdk/blob/master/.travis.yml)が実行されており、最新の LTS バージョンで全てのユニットテストが成功していることを期待することができます。SDK 開発チームは、全ての OpenJDK ディストリビューションでの網羅的な QA テストまでは実施していませんが、問題はまず発生しないはずです。

---

## <!--Getting Help--> ヘルプ

何か困ったことがあったら、以下の場所でサポートを受けてみてください。

* [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) にバグや機能要望を報告する（**必ず英語でお願いします**）
* [Slack Developer Community](https://slackcommunity.com/) で **Slack SDK for Java** の使い方についてヘルプを求めたり、他の開発者たちとつながる
