---
lang: ja
---

# Slack SDK for Java

**Slack SDK for Java** は Java らしいアプローチで全ての Slack プラットフォームの公開機能をサポートする公式 SDK です。この SDK は全て Java で書かれており、開発者はこの SDK を Java だけでなく Kotlin、Groovy、Scala といった JVM 言語でも利用できます。

この SDK は大きく分けて主に二種類のモジュールを提供しています。

* [**Bolt️ for Java**](/java-slack-sdk/guides/bolt-basics) は、最新の Slack アプリをシンプルな API を用いて簡単に開発するためのフレームワークです
* [**Slack API クライアント**](/java-slack-sdk/guides/web-api-basics) は、より柔軟に Slack アプリを開発したい場合にこれだけを組み込んで使用することができます

## <!--Requirements--> 動作条件

この SDK は **OpenJDK 8 かそれよりも新しい LTS バージョン** での動作を保証しています。

全てのパッチリリースが[全ての LTS バージョンでの基本的な CI ビルド](https://github.com/slackapi/java-slack-sdk/blob/main/.travis.yml)が実行されており、利用者は最新の LTS バージョンで全てのユニットテストが成功していることを期待することができます。SDK 開発チームは、全ての OpenJDK ディストリビューションでの網羅的な QA テストまでは実施していませんが、問題はまず発生しないはずです。

## <!--Getting Help--> ヘルプ

何か困ったことがあったら、以下の場所でサポートを受けてみてください。

* [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) にバグや機能要望を報告する（**必ず英語でお願いします**）
* [Slack Developer Community](https://slackcommunity.com/) で **Slack SDK for Java** の使い方についてヘルプを求めたり、他の開発者たちとつながる
