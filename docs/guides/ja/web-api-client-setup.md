---
layout: ja
title: "API クライアントのセットアップ"
lang: ja
---

# <!--API Client Installation--> API クライアントのセットアップ

<!--
Installing **slack-api-client** into your Java project is the first step to use Slack API clients. This guide shows you how to setup using [Maven](https://maven.apache.org/), [Gradle](https://gradle.org/), and by building from source on your own. 
-->

Slack API クライアントを利用するための最初のステップは、**slack-api-client** をあなたの Java プロジェクトにインストールすることです。このガイドでは、[Maven](https://maven.apache.org/)、[Gradle](https://gradle.org/) を使用したときのそれぞれのセットアップ手順と、このプロジェクトを手元でソースコードからビルドするときの手順を説明します。

## <!--Prerequisites-->前提条件

<!--
Getting OpenJDK 8 and higher LTS versions installed beforehand is required. As long as using a supported JDK version, this SDK should be working with any of OpenJDK distributions.
-->
事前に OpenJDK 8 またはそれより新しい LTS バージョンがインストールされている必要があります。サポートされている JDK バージョンを利用している限り、この SDK は全ての OpenJDK ディストリビューションで動作するはずです。

---

## Maven

<!-- 
Let's start with how to installation with Maven. As **slack-api-client** is a library dependency, there is no requirement of Maven versions.
-->
Maven でのライブラリインストールをはじめましょう。**slack-api-client** はただの依存ライブラリですので、Maven のバージョンについては特に指定はありません。

### pom.xml

<!--
Save `pom.xml` with the following XML definition in the root directory of your Java project. As you see, this is a commonplace Maven project. No specific settings would be needed to load the project on your favorite IDE.
-->
以下の XML 設定を含む `pom.xml` をあなたの Java プロジェクトのルートディレクトリに配置しましょう。見ての通り、ごく普通の Maven プロジェクトです。あなたのお好みの IDE でこのプロジェクトを読み込むにあたって、何も特別な設定は必要ありません。


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

<!--
Create a new Java class named **Example** that has a main method to run. I know the following code is not actually useful. For now,  place it just for verifying the build settings are valid.
-->

実行可能な main メソッドを持つ **Example** という名前の class を新しく定義しましょう。このサンプルコードが全く実用的ではないのは重々承知しています。このタイミングでは、とりあえずはこのプロジェクトの設定が正しいかどうかを確認するために、このコードをコピーして保存してみてください。

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

<!--
Run the **Example.main(String[])** from your IDE or by running the following command.
-->
**Example.main(String[])** をお好みの IDE から、または以下のコマンドで実行してみましょう。


```bash
mvn compile exec:java \
  -Dexec.cleanupDaemonThreads=false \
  -Dexec.mainClass="Example"
```

<!--
If you see the following stdout, your installation has succeeded!
-->
もしあなたが以下の標準出力を見ているなら、成功です！

```
ApiTestResponse(ok=true, args=ApiTestResponse.Args(foo=bar, error=null), warning=null, error=null, needed=null, provided=null)
```

<!--
In summary, the things you've done here are:
-->
つまり、ここで行ったことは以下の通りです。

<!--
* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Maven installed (if not, run `brew install maven` for macOS / visit [their website](https://maven.apache.org/) for others)
* ✅ `pom.xml` having **slack-api-client** as a dependency
* ✅ `src/main/java/Example.java` with a main method
-->
* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Maven をインストール（もしまだであれば macOs は `brew install maven` を実行 / 他の OS 環境の場合は [公式サイト](https://maven.apache.org/) へアクセス）
* ✅ `pom.xml` に **slack-api-client** を依存ライブラリとして追加
* ✅ main メソッドを持つ `src/main/java/Example.java` を作成

---

## Gradle

<!--
Do you prefer using Gradle? Let's try installing the library with Gradle. The steps are quite similar to Maven but there are some differences.
-->
Gradle の方がお好みですか？それでは Gralde を使ってライブラリをインストールしてみましょう。手順は Maven にかなり似ていて、いくつか差分があるだけです。

### build.gradle

<!--
Place `build.gradle` in the root directory of your Java project. We don't have any requirement of Gradle versions.
-->
以下の内容で `build.gradle` をあなたの Java プロジェクトのルートディレクトリに配置します。Gradle のバージョンに特に指定はありません。

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

<!--
As with the Maven section, let's create a class named **Example** with a main method. Then, run it from your IDE or hit `gradle run` on your terminal. You'll see the same stdout.
-->
Maven のセクションでも紹介した通り、main メソッドを持つ **Example** という class を作りましょう。その上で IDE から、または `gradle run` をターミナルから実行してみてください。Maven のときと同様の標準出力が確認できるはずです。

<!--
In summary, the things you've done here are:
-->
つまり、ここで行ったことは以下の通りです。

<!--
* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` having **slack-api-client** as a dependency
* ✅ `src/main/java/Example.java` with a main method
-->
* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOs は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に **slack-api-client** を依存ライブラリとして追加
* ✅ main メソッドを持つ `src/main/java/Example.java` を作成


---

## <!--Gradle for Kotlin--> Gradle を使った Kotlin プロジェクトの設定

<!--
In this guide, we sometimes use Kotlin code examples for simplicity. To try those examples, setup a Kotlin project with Gradle as below.
-->
このガイドでは、コードを簡潔にするために Kotlin のコードサンプルが出てくることがあります。それらのサンプルを試すために Gradle を使った以下の手順で Kotlin プロジェクトをセットアップしておきましょう。

### build.gradle

<!--The build settings are almost the same as above except for some Kotlin-specific parts.-->
設定は上記の Gradle のものとほぼ同様ですが、いくつか Kotlin 固有の設定もあります。

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "1.3.61" // 最新の Kotlin バージョンを指定してください
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

As you see, the code using **slack-api-client** in Kotlin is much more concise than Java. Run the following code from your IDE or by `gradle run`.

```kotlin
import com.slack.api.Slack

fun main() {
  val slack = Slack.getInstance()
  val response = slack.methods().apiTest { it.foo("bar") }
  println(response)
}
```

**NOTE**: A name of a source file in Kotlin needs to end with **.kt**, not **.java**.

In summary, the things you've done here are:

* ✅ JDK 8 or higher installed (if not, run `brew install openjdk@11` for macOS / visit [OpenJDK website](https://openjdk.java.net/install/) for others)
* ✅ Gradle installed (if not, run `brew install gradle` for macOS / visit [their website](https://gradle.org/) for others)
* ✅ `build.gradle` having valid Kotlin language settings and **slack-api-client** as a dependency
* ✅ `src/main/java/Example.java` with a main method

---

## Build from Source

You may want to build the latest revision on your own. In the case of building from source, go with the following steps.

```bash
git clone git@github.com:slackapi/java-slack-sdk.git
cd java-slack-sdk
mvn install -Dmaven.test.skip=true
```

Just by doing above, all the SDK modules will be available under `$HOME/.m2/repository`, so that now you can use them on the machine. If you use Gradle, make sure to add `mavenLocal()` to `repositories` in your `build.gradle`.

```groovy
repositories {
  mavenLocal()
}
```

That's all set! If you get stuck, we’re here to help. The following are the best ways to get assistance working through your issue:

* Use our [GitHub Issue Tracker](http://github.com/slackapi/java-slack-sdk/issues) for reporting bugs or requesting features
* Visit the [Slack Developer Community](https://slackcommunity.com/) for getting help using **Slack SDK for Java** or just generally bond with your fellow Slack developers.
