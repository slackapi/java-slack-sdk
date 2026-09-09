# Bolt 入門

**Bolt for Java** は、最新のプラットフォーム機能を使った Slack アプリの開発をスピーディに行うための抽象レイヤーを提供するフレームワークです。

このガイドでは、初めての Bolt アプリを開発する手順を紹介します。

なお Slack アプリ開発全般についてまだ不慣れな方は、まず「[An introduction to Slack apps（英語）](/)」に軽く目を通した方がよいかもしれません。

---
## プロジェクトのセットアップ

では、さっそく Bolt を使った Slack アプリ開発を始めましょう！このガイドでは Maven、Gradle を使ったプロジェクトセットアップの手順を説明します。

### Maven

[Maven プロジェクトを作成](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)した後、まずは **bolt** 依存ライブラリを `pom.xml` に追加します。このライブラリ自体は特定の環境に依存していません。Bolt を [Spring Boot](https://spring.io/projects/spring-boot)、[Quarkus (Undertow)](https://quarkus.io/) やその他 Servlet 環境で利用する場合は **bolt-servlet** というライブラリも追加します。単に **bolt-servlet** だけを追加しても OK です。

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>sdkLatestVersion</version>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-servlet</artifactId>
  <version>sdkLatestVersion</version>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-simple</artifactId>
  <version>slf4jApiVersion</version>
</dependency>
```

そのような他のフレームワークは一切使わず、シンプルな構成で Jetty HTTP サーバーで起動したい場合は **bolt-jetty** を追加してください。もし [Jakarta EE Servlet API](https://jakarta.ee/specifications/servlet/5.0/)互換の最新の Jetty サーバーを使いたい場合は **bolt-jakarta-jetty** を利用してください。

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-jetty</artifactId> <!-- "bolt" と　"bolt-servlet" はこれの依存として解決されます -->
  <version>sdkLatestVersion</version>
</dependency>
```

また、コンパイラーの source/target 言語の設定を最低でも 1.8 以上にしておく必要があります。

```xml
<properties>
  <maven.compiler.source>1.8</maven.compiler.source>
  <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

### Gradle

Gradle プロジェクトを作成した後 **bolt** 関連の依存ライブラリを `build.gradle` に追加してください。

```groovy
dependencies {
  implementation("com.slack.api:bolt:sdkLatestVersion")
  implementation("com.slack.api:bolt-servlet:sdkLatestVersion")
  implementation("com.slack.api:bolt-jetty:sdkLatestVersion")
  implementation("org.slf4j:slf4j-simple:slf4jApiVersion")
}
```

---
## 3 分間で動かす Bolt アプリ

### **bolt-jetty** の利用

**bolt-jetty** は Slack アプリサーバーを起動する手軽な手段です。このモジュールを使えば、開発者は **App** インスタンスを初期化して HTTP サーバーを起動する main メソッドを書くだけで Slack アプリバックエンドサービスを立ち上げることができます。

#### build.gradle

以下のビルド設定は、そのままコピーして使うことができます。プロジェクトのルートディレクトリに配置してください。

```groovy
plugins {
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation("com.slack.api:bolt-jetty:sdkLatestVersion")
  implementation("org.slf4j:slf4j-simple:slf4jApiVersion")
}
application {
  mainClassName = "hello.MyApp"
}
run {
  // gradle run -DslackLogLevel=debug
  systemProperty "org.slf4j.simpleLogger.log.com.slack.api", System.getProperty("slackLogLevel")
}
```

#### src/main/java/hello/MyApp.java

このフレームワークを使ったコーディングは想像以上に簡単です。

はじめての Bolt アプリを動かすためにはたった一つのソースコードだけが必要です。必要なことは **SlackAppServer** を起動させる main メソッドを定義することだけです。デフォルトの設定では、このサーバーは 3000 ポートをリッスンしますが、設定によって変更可能です。変更の方法は、このクラスの他のコンストラクターを確認してみてください。

```java
package hello;

import com.slack.api.bolt.App;
// bolt-jakarta-jetty を使う場合は `com.slack.api.bolt.jakarta_jetty.SlackAppServer` を import してください
import com.slack.api.bolt.jetty.SlackAppServer;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET という環境変数が設定されている前提
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":candy: はい、アメちゃん！");
    });

    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
```

Java 10 以上のバージョンを使えば [Local Variable Type Inference](https://developer.oracle.com/java/jdk-10-local-variable-type-inference.html) によって上記のコードはもう少し簡潔になるでしょう。例えば OpenJDK 11 をインストールして、以下のように `build.gradle` で Java のバージョンを指定します。お使いの IDE でも同じ設定にします。

```groovy
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}
```

これによって、同じ型を繰り返し記述する必要がなくなります。

```java
var app = new App();
app.command("/hello", (req, ctx) -> {
  return ctx.ack(":candy: はい、アメちゃん！");
});
var server = new SlackAppServer(app);
server.start();
```

### 環境変数を設定して起動

**App** のデフォルトコンストラクタは、アプリの起動時に、以下の二つの環境変数が設定されていることを期待します。

|環境変数名|説明|
|-|-|
|**SLACK_BOT_TOKEN**|開発用ワークスペース（Development Workspace）での有効なボットトークン（形式は `xoxb-` から始まります）です。このボットトークンを発行するには Slack アプリを開発用ワークスペースにインストールする必要があります。[Slack アプリ管理画面](https://api.slack.com/apps)にアクセスして、開発中のアプリを選択、左ペインの **Settings** > **Install App** から実行します（「Please add at least one feature or permission scope to install your app.」というメッセージが表示されている場合は　[`app_mentions:read`](/reference/scopes/app_mentions.read) bot scope を追加してください）。 <br/><br/>複数のワークスペースにインストール可能なアプリとして実行する場合はこの環境変数を設定する必要はありません。そのようなアプリの開発については「[アプリの配布 (OAuth)](/tools/java-slack-sdk/guides/app-distribution)」を参考にしてください。|
|**SLACK_SIGNING_SECRET**|この秘密の値は Slack プラットフォームとだけ共有する情報です。これは Slack アプリが受けたリクエストが本当に Slack API サーバーからのリクエストであるかを検証するために使用します。Slack アプリは公開されたエンドポイントを持つため、リクエストの検証はセキュリティのために重要です。この値は [Slack アプリ管理画面](https://api.slack.com/apps)にアクセスして、開発中のアプリを選択、左ペインの **Settings** > **Basic Information** へ遷移して **App Credentials** > **Signing Secret** の情報を表示させると確認できます。より詳細な情報は「[Verifying requests from Slack（英語）](/authentication/verifying-requests-from-slack)」を参考にしてください。|

なお、**App** を別の方法（例: 規定の環境変数名を使わない）で初期化したい場合は **AppConfig** を自前で初期化するコードを書いてください。

上記の二つの環境変数を設定した上で、ターミナル上でアプリを実行してみましょう。

- Gradle の場合: `gradle run` (より詳細なログを表示したい場合は `gradle run -DslackLogLevel=debug`)
- Maven の場合: `mvn compile exec:java -Dexec.mainClass="hello.MyApp"` (より詳細なログを表示したい場合は `-Dorg.slf4j.simpleLogger.defaultLogLevel=debug` を指定)

このコマンドは、先ほど定義した main メソッドを実行します。

```bash
# https://api.slack.com/apps にアクセスして取得
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_SIGNING_SECRET=123abc...your-own-valid-one

# main メソッドを実行して、サーバープロセスを起動
# Gradle の場合
gradle run
# Maven の場合
mvn compile exec:java -Dexec.mainClass="hello.MyApp"
```

標準出力に "**⚡️ Bolt app is running!**" というメッセージが表示されているはずです。

もしうまくいかない場合は、以下のチェックリストを見直してみてください。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOS は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に **bolt-jetty** 依存ライブラリを追加、適切な **application** プラグイン設定も追加
* ✅ main メソッドを持つ `src/main/java/hello/MyApp.java` を作成
* ✅ [Slack アプリをつくり](https://api.slack.com/apps?new_app=1) [`app_mentions:read`](/reference/scopes/app_mentions.read) という Bot Token Scope を追加、アプリを開発用ワークスペースにインストール
* ✅ [Slack アプリ管理画面](https://api.slack.com/apps) から [**Bot User OAuth Access Token**](/authentication/tokens#bot) と [**Signing Secret**](/authentication/verifying-requests-from-slack) の値をコピーしてきて環境変数に設定

### `/hello` コマンドの有効化

Bolt アプリは起動できました！しかし、コードの中で定義した `/hello` というスラッシュコマンドはまだ使えません。これを有効にするには以下の手順を行ってください。

* Slack API サーバーから起動した Bolt アプリにアクセスできるようにする
  * よく知られているのは [ngrok](https://ngrok.com/) を使う方法です（インストールして `ngrok http 3000` を別のターミナルで実行）
* Slack アプリを設定して再インストール
  * [Slack アプリ管理画面](https://api.slack.com/apps) にアクセス
  * 開発中のアプリを選択して、左ペインから **Features** > **Slash Commands** へ遷移
  * **Create New Command** ボタンをクリック
  * ダイアログ内で必要なコマンドの情報を入力
    * **Command**: `/hello`
    * **Request URL**: `https://{あなたのドメイン}/slack/events` (ngrok だと `https://{ランダム}.ngrok.io/slack/events`)
    * **Short Description**: お好きな内容で
  * **Save** ボタンをクリック
  * **Settings** > **Install App** に遷移して **Reinstall App** ボタンをクリック

これで `/hello` コマンドが開発用ワークスペースで利用できるようになっているはずです。そして、Bolt アプリが正常に動作していれば、コマンド実行に「`🍬 はい、アメちゃん！`」という返事が返ってくるはずです。

### (参考) Spring Boot での設定

[Spring Boot](https://spring.io/projects/spring-boot) は、Java の世界で最も人気のある Web フレームワークの一つです。Bolt を Spring Boot と共存させる方法について興味を持っている方も多いかと思います。

ご心配なく！Spring Boot アプリに Bolt を（Spring コンポーネントとして） _inject_ することはとても簡単ですぐにできます。

やることは `build.gradle` （Gradle の場合）内の `implementation("com.slack.api:bolt:sdkLatestVersion")` を `dependencies` に追加して数行のコードを追加で書くことだけです。

```java
@Configuration
public class SlackApp {
  @Bean
  public App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> ctx.ack(":candy: はい、アメちゃん！"));
    return app;
  }
}

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
  public SlackAppController(App app) {
    super(app);
  }
}
```


より詳細な情報は[こちらのガイド](/tools/java-slack-sdk/guides/supported-web-frameworks)を参考にしてください。

---
## Kotlin での設定

コードをより簡潔にするために Java の代わりに [Kotlin](https://kotlinlang.org/) で Bolt アプリを書くことはとても良い選択肢です。このセクションでは、Bolt アプリを Kotlin で開発するための設定手順を紹介します。

#### build.gradle

ここでのビルド設定のほとんどは Kotlin 言語を有効にするために必要なものです。**bolt-jetty** 依存ライブラリを追加していることが唯一 Bolt に固有の設定です。

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "kotlinVersion"
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:bolt-jetty:sdkLatestVersion")
  implementation("org.slf4j:slf4j-simple:slf4jApiVersion") // または logback-classic など
}
application {
  mainClassName = "MyAppKt" // ソースファイル名の末尾、拡張子の代わりに "Kt" をつけた命名になります
}
```

もしすでに Kotlin に詳しくて、Gradle Kotlin DSL を使いたい場合、もちろんそれも全く問題ありません。

#### src/main/kotlin/MyApp.kt

これは Bolt アプリをローカルマシンで起動するために必要最低限のコードを含むソースファイルです。

```kotlin
import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer

fun main() {
  val app = App()

  // ここで何かする

  val server = SlackAppServer(app)
  server.start() // http://localhost:3000/slack/events
}
```

### 動作確認

これで全て完了です。念のため、チェックリストを確認しておきましょう。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOS は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に適切な Kotlin の言語設定と **bolt-jetty** 依存ライブラリを追加
* ✅ main メソッドを持つ `src/main/kotlin/MyApp.kt` を作成
* ✅ [Slack アプリをつくり](https://api.slack.com/apps?new_app=1) [`app_mentions:read`](/reference/scopes/app_mentions.read) という Bot Token Scope を追加、アプリを開発用ワークスペースにインストール
* ✅ [Slack アプリ管理画面](https://api.slack.com/apps) から [**Bot User OAuth Access Token**](/authentication/tokens#bot) と [**Signing Secret**](/authentication/verifying-requests-from-slack) の値をコピーしてきて環境変数に設定

すべてが OK ✅であれば、あなたのはじめての Kotlin を使った Bolt アプリが正常に起動するはずです。

```bash
# https://api.slack.com/apps にアクセスして取得
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_SIGNING_SECRET=123abc...your-own-valid-one

# main メソッドを実行して、サーバープロセスを起動
gradle run
```

... 標準出力に "**⚡️ Bolt app is running!**" というメッセージが表示されましたか？

もし表示されていれば、万事うまくいっています！ 🎉

ここからやることはコードを書いて必要に応じてアプリをリスタートするだけです。Kotlin での Bolt アプリ開発を楽しんでください！ 👋

**Pro tip**: もしあなたがあまり IDE を使うことが好みでないとしても Kotlin を使うなら [IntelliJ IDEA](https://www.jetbrains.com/idea/) を使うことを強くおすすめします。この IDE を使うことがもっともスムースな Kotlin アプリ開発の方法です。

---
## 次のステップ

「[Bolt の概要](/tools/java-slack-sdk/guides/bolt-basics)」を読んでさらに理解を深めてください。

Spring Boot や Micronaut、Quarkus、Helidon SE で動かす方法を知りたければ「[対応 Web フレームワーク](/tools/java-slack-sdk/guides/supported-web-frameworks)」を参考にしてください。

また、以下のように、多くのサンプル例がプロジェクトの GitHub リポジトリ内にあるので、あわせて参考にしてみてください。

* [Spring Boot を使ったサンプル例](https://github.com/slackapi/java-slack-sdk/tree/main/bolt-spring-boot-examples)
* [Micronaut を使ったサンプル例](https://github.com/slackapi/java-slack-sdk/tree/main/bolt-micronaut/src/test/java/example)
* [Quarkus を使ったサンプル例](https://github.com/slackapi/java-slack-sdk/tree/main/bolt-quarkus-examples)
* [Kotlin で書かれたサンプル例](https://github.com/slackapi/java-slack-sdk/tree/main/bolt-kotlin-examples)
* [Docker を使ったサンプル例](https://github.com/slackapi/java-slack-sdk/tree/main/bolt-docker-examples)
