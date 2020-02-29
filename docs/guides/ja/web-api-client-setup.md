---
layout: ja
title: "API クライアントのセットアップ"
lang: ja
---

# <!--API Client Installation--> API クライアントのセットアップ

Slack API クライアントを利用するための最初のステップは、**slack-api-client** をあなたの Java プロジェクトにインストールすることです。このガイドでは、[Maven](https://maven.apache.org/)、[Gradle](https://gradle.org/) を使用したときのそれぞれのセットアップ手順と、このプロジェクトを手元でソースコードからビルドするときの手順を説明します。

## <!--Prerequisites-->前提条件

事前に OpenJDK 8 またはそれより新しい LTS バージョンが[インストール](https://openjdk.java.net/install/)されている必要があります。サポートされている JDK バージョンを利用している限り、この SDK は全ての OpenJDK ディストリビューションで動作するはずです。

---

## Maven

Maven でのライブラリインストールをはじめましょう。**slack-api-client** はただの依存ライブラリですので、Maven のバージョンについては特に指定はありません。

### pom.xml

以下の XML 設定を含む `pom.xml` をあなたの Java プロジェクトのルートディレクトリに配置しましょう。見ての通り、ごく普通の Maven プロジェクトです。あなたのお好みの IDE でこのプロジェクトを読み込むにあたって特別な設定は一切必要ありません。


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
      <artifactId>slack-api-client</artifactId>
      <version>{{ site.sdkLatestVersion }}</version>
    </dependency>
  </dependencies>
</project>
```

### src/main/java/Example.java

実行可能な main メソッドを持つ **Example** という名前の class を新しく定義しましょう。このサンプルコードが全く実用的ではないのは重々承知しています。ただ、とりあえずこのプロジェクトの設定が正しいかどうかを確認するために、このコードをコピーして保存してみてください。

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.api.ApiTestResponse;

public class Example {
  public static void main(String[] args) throws Exception {
    Slack slack = Slack.getInstance();
    ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
    System.out.println(response);
  }
}
```

**Example.main(String[])** をお好みの IDE から、または以下のコマンドで実行してみましょう。


```bash
mvn compile exec:java \
  -Dexec.cleanupDaemonThreads=false \
  -Dexec.mainClass="Example"
```

以下の内容が標準出力されていれば成功です！

```
ApiTestResponse(ok=true, args=ApiTestResponse.Args(foo=bar, error=null), warning=null, error=null, needed=null, provided=null)
```

ここで行ったことをまとめると以下のようになります。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Maven をインストール（もしまだであれば macOs は `brew install maven` を実行 / 他の OS 環境の場合は [公式サイト](https://maven.apache.org/) へアクセス）
* ✅ `pom.xml` に **slack-api-client** を依存ライブラリとして追加
* ✅ main メソッドを持つ `src/main/java/Example.java` を作成

---

## Gradle

普段 Maven よりも Gradle をお使いですか？では Gralde を使って同様に設定してみましょう。手順は Maven にかなり似ていて、いくつか差分があるだけです。

### build.gradle

以下の内容で `build.gradle` を Java プロジェクトのルートディレクトリに配置します。Gradle のバージョンに特に指定はありません。

```groovy
plugins {
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation("com.slack.api:slack-api-client:{{ site.sdkLatestVersion }}")
}
application {
  mainClassName = "Example"
}
```

Maven のセクションでも紹介した通り、main メソッドを持つ **Example** という class を作りましょう。そして IDE から、または `gradle run` をターミナルから実行してみてください。Maven のときと同様の標準出力が確認できるはずです。

ここで行ったことをまとめると以下のようになります。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOs は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に **slack-api-client** を依存ライブラリとして追加
* ✅ main メソッドを持つ `src/main/java/Example.java` を作成


---

## <!--Gradle for Kotlin--> Gradle を使った Kotlin プロジェクトの設定

このガイドの他のページでは、コードを簡潔にするために Kotlin のコードサンプルが出てくることがあります。それらのサンプルを試すために Gradle を使った以下の手順で Kotlin プロジェクトをセットアップしておきましょう。

### build.gradle

設定は上記の Java のものとほぼ同様ですが、いくつか Kotlin 固有の設定もあります。

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "{{ site.kotlinVersion }}" // 最新の Kotlin バージョンを指定してください
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:slack-api-client:{{ site.sdkLatestVersion }}")
}
application {
  mainClassName = "ExampleKt" // main 関数を持つソースファイルの名前の末尾に "Kt" をつけてください
}
```

### src/main/kotlin/Example.kt

ご覧の通り、Kotlin で **slack-api-client** を使ったコードは Java よりもかなり簡潔になります。IDE または `gradle run` で実行してみてください。

```kotlin
import com.slack.api.Slack

fun main() {
  val slack = Slack.getInstance()
  val response = slack.methods().apiTest { it.foo("bar") }
  println(response)
}
```

**注**: Kotlin のソースファイル名は **.java** ではなく **.kt** の拡張子である必要があります

ここで行ったことをまとめると以下のようになります。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOs は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に 適切な Kotlin の言語関連の設定と **slack-api-client** を依存ライブラリとして追加
* ✅ main メソッドを持つ `src/main/kotlin/Example.kt` を作成

---

## <!--Build from Source-->ソースからビルド

最新のリビジョンのソースコードからビルドして使いたいということがあるかもしれません。ソースからビルドするには以下の手順に従います。

```bash
git clone git@github.com:slackapi/java-slack-sdk.git
cd java-slack-sdk
mvn install -Dmaven.test.skip=true
```

上記を実行するとこの SDK の全てのモジュールが `$HOME/.m2/repository` 配下に配置されるので、そのホスト上で利用可能になります。Gradle をお使いの場合は `build.gradle` 内の `repositories` に `mavenLocal()` を追加してください。

```groovy
repositories {
  mavenLocal()
}
```

以上です！何か困ったことがあったら、以下の場所でサポートを受けてみてください。

* [GitHub Issue Tracker](https://github.com/slackapi/java-slack-sdk/issues) にバグや機能要望を報告する（**必ず英語でお願いします**）
* [Slack Developer Community](https://slackcommunity.com/) で **Slack SDK for Java** の使い方についてヘルプを求めたり、他の開発者たちとつながる
