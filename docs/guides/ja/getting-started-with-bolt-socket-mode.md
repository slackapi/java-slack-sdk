---
layout: ja
title: "Bolt 入門 (ソケットモード)"
lang: ja
---

# Bolt 入門 (ソケットモード)

**Bolt for Java** は、最新のプラットフォーム機能を使った Slack アプリの開発をスピーディに行うための抽象レイヤーを提供するフレームワークです。

このガイドでは、初めての Bolt アプリを開発する手順を紹介します。

* プロジェクトのセットアップ
  * [Maven](#maven)
  * [Gradle](#gradle)
* 3 分間で動かす Bolt アプリ
  * **bolt-socket-mode** の利用
  * 環境変数を設定して起動
  * `/hello` コマンドの有効化
* Kotlin での設定
  * 動作確認
* 次のステップ

なお Slack アプリ開発全般についてまだ不慣れな方は、まず「[An introduction to Slack apps（英語）](https://api.slack.com/start/overview)」に軽く目を通した方がよいかもしれません。

---
## プロジェクトのセットアップ

では、さっそく Bolt を使った Slack アプリ開発を始めましょう！このガイドでは Maven、Gradle を使ったプロジェクトセットアップの手順を説明します。

### Maven

Maven プロジェクトを作成した後、まずは **bolt** 依存ライブラリを `pom.xml` に追加します。このライブラリ自体は特定の環境に依存していません。[ソケットモード](https://api.slack.com/apis/connections/socket)を有効にするためには **bolt-socket-mode** というライブラリとその provided スコープの必要な依存ライブラリも合わせて追加してください。

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
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
```

もし、Java-WebSocket を使いたい場合は **tyrus-standalone-client** の代わりにそのライブラリを追加します。

```xml
<dependency>
  <groupId>org.java-websocket</groupId>
  <artifactId>Java-WebSocket</artifactId>
  <version>1.5.1</version>
</dependency>
```

### Gradle

Gralde プロジェクトを作成した後 **bolt** 関連の依存ライブラリを `build.gradle` に追加してください。

```groovy
dependencies {
  implementation("com.slack.api:bolt:{{ site.sdkLatestVersion }}")
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
}
```

---
## 3 分間で動かす Bolt アプリ

### **bolt-socket-mode** の利用

**bolt-socket-mode** は[ソケットモード](https://api.slack.com/apis/connections/socket)の Slack アプリを起動する手軽な手段です。このモジュールを使えば、開発者は **App** インスタンスを初期化して処理をスタートする main メソッドを書くだけで WebSocket コネクションを確立することができます。

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
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
  implementation("org.slf4j:slf4j-simple:1.7.30")
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

はじめての Bolt アプリを動かすためにはたった一つのソースコードだけが必要です。必要なことは **SocketModeApp** を起動させる main メソッドを定義することだけです。

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;

public class MyApp {
  public static void main(String[] args) throws Exception {
    // SLACK_BOT_TOKEN という環境変数が設定されている前提
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    // SLACK_APP_TOKEN という環境変数が設定されている前提
    new SocketModeApp(app).start();
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
  return ctx.ack(":wave: Hello!");
});
new SocketModeApp(app).start();
```

### 環境変数を設定して起動

**App** のデフォルトコンストラクタは、アプリの起動時に、以下の二つの環境変数が設定されていることを期待します。

|環境変数名|説明|
|-|-|
|**SLACK_BOT_TOKEN**|開発用ワークスペース（Development Workspace）での有効なボットトークン（形式は `xoxb-` から始まります）です。このボットトークンを発行するには Slack アプリを開発用ワークスペースにインストールする必要があります。[Slack アプリ管理画面](http://api.slack.com/apps)にアクセスして、開発中のアプリを選択、左ペインの **Settings** > **Install App** から実行します（「Please add at least one feature or permission scope to install your app.」というメッセージが表示されている場合は　[`app_mentions:read`](https://api.slack.com/scopes/app_mentions:read) bot scope を追加してください）。 <br/><br/>複数のワークスペースにインストール可能なアプリとして実行する場合はこの環境変数を設定する必要はありません。そのようなアプリの開発については「[アプリの配布 (OAuth)]({{ site.url | append: site.baseurl }}/guides/ja/app-distribution)」を参考にしてください。|
|**SLACK_SIGNING_SECRET**|この Slack アプリの有効なアプリレベルトークン（形式は `xapp-` から始まります）です。トークンを発行するには、[Slack アプリ管理画面](http://api.slack.com/apps)にアクセスして、開発中のアプリを選択、左ペインの **Settings** > **Basic Information** > **App-Level Tokens** へ移動し、`connections:write` というスコープにしたトークンを作成します。|

なお、**App** を別の方法（例: 規定の環境変数名を使わない）で初期化したい場合は **AppConfig** を自前で初期化するコードを書いてください。

ともあれ、上記の二つの環境変数を設定した上で、ターミナル上で `gradle run` を実行してみましょう。このコマンドは、先ほど定義した main メソッドを実行します。より詳細なログ出力を見たい場合は `gradle run -DslackLogLevel=debug` のようにしてください。

```bash
# https://api.slack.com/apps にアクセスして取得
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_APP_TOKEN=xapp-...your-own-valid-one

# main メソッドを実行して WebSocket 接続するプロセスを起動
gradle run
```

標準出力に "**⚡️ Bolt app is running!**" というメッセージが表示されているはずです。

もしうまくいかない場合は、以下のチェックリストを見直してみてください。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOS は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に **bolt-socket-mode** 依存ライブラリを追加、適切な **application** プラグイン設定も追加
* ✅ main メソッドを持つ `src/main/java/hello/MyApp.java` を作成
* ✅ [Slack アプリをつくり](https://api.slack.com/apps?new_app=1) [`commands`](https://api.slack.com/scopes/commands) という Bot Token Scope を追加、**`connections:write` スコープを設定したアプリレベルトークンを作成**、アプリを開発用ワークスペースにインストール
* ✅ [Slack アプリ管理画面](https://api.slack.com/apps) から [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) と [**App-Level Token**](https://api.slack.com/docs/token-types#app) の値をコピーしてきて環境変数に設定

### `/hello` コマンドの有効化

Bolt アプリは起動できました！しかし、コードの中で定義した `/hello` というスラッシュコマンドはまだ使えません。これを有効にするには以下の手順を行ってください。

* [Slack アプリ管理画面](https://api.slack.com/apps) にアクセス
* 開発中のアプリを選択
* 左ペインから **Settings** > **Socket Mode** へ遷移
  * **Enable Socket Mode** を有効化
* 左ペインから **Features** > **Slash Commands** へ遷移
  * **Create New Command** ボタンをクリック
  * ダイアログ内で必要なコマンドの情報を入力
    * **Command**: `/hello`
    * **Short Description**: お好きな内容で
  * **Save** ボタンをクリック

これで `/hello` コマンドが開発用ワークスペースで利用できるようになっているはずです。そして、Bolt アプリが正常に動作していれば、コマンド実行に「`🍬 はい、アメちゃん！`」という返事が返ってくるはずです。

---
## Kotlin での設定

コードをより簡潔にするために Java の代わりに [Kotlin](https://kotlinlang.org/) で Bolt アプリを書くことはとても良い選択肢です。このセクションでは、Bolt アプリを Kotlin で開発するための設定手順を紹介します。

#### build.gradle

ここでのビルド設定のほとんどは Kotlin 言語を有効にするために必要なものです。**bolt-socket-mode** 依存ライブラリを追加していることが唯一 Bolt に固有の設定です。

```groovy
plugins {
  id("org.jetbrains.kotlin.jvm") version "{{ site.kotlinVersion }}"
  id("application")
}
repositories {
  mavenCentral()
}
dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.slack.api:bolt-socket-mode:{{ site.sdkLatestVersion }}")
  implementation("javax.websocket:javax.websocket-api:1.1")
  implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.17")
  implementation("org.slf4j:slf4j-simple:1.7.30") // または logback-classic など
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
import com.slack.api.bolt.socket_mode.SocketModeApp

fun main() {
  val app = App()

  // ここで何かする

  SocketModeApp(app).start()
}
```

### 動作確認

これで全て完了です。念のため、チェックリストを確認しておきましょう。

* ✅ JDK 8 またはそれよりも新しいバージョンをインストール（もしまだであれば macOS は `brew install openjdk@11` を実行 / 他の OS 環境の場合は [OpenJDK のウェブサイト](https://openjdk.java.net/install/) へアクセス）
* ✅ Gradle をインストール（もしまだであれば macOS は `brew install gradle` を実行 / 他の OS 環境の場合は [公式サイト](https://gradle.org/) へアクセス）
* ✅ `build.gradle` に適切な Kotlin の言語設定と **bolt-socket-mode** 依存ライブラリを追加
* ✅ main メソッドを持つ `src/main/kotlin/MyApp.kt` を作成
* ✅ [Slack アプリをつくり](https://api.slack.com/apps?new_app=1) [`commands`](https://api.slack.com/scopes/commands) という Bot Token Scope を追加、**`connections:write` スコープを設定したアプリレベルトークンを作成**、アプリを開発用ワークスペースにインストール
* ✅ [Slack アプリ管理画面](https://api.slack.com/apps) から [**Bot User OAuth Access Token**](https://api.slack.com/docs/token-types#bot) と [**App-Level Token**](https://api.slack.com/docs/token-types#app) の値をコピーしてきて環境変数に設定

すべてが OK ✅であれば、あなたのはじめての Kotlin を使った Bolt アプリが正常に起動するはずです。

```bash
# Visit https://api.slack.com/apps to know these
export SLACK_BOT_TOKEN=xoxb-...your-own-valid-one
export SLACK_APP_TOKEN=xapp-...your-own-valid-one

# run the main function
gradle run
```

... 標準出力に "**⚡️ Bolt app is running!**" というメッセージが表示されましたか？

もし表示されていれば、万事うまくいっています！ 🎉

ここからやることはコードを書いて必要に応じてアプリをリスタートするだけです。Kotlin での Bolt アプリ開発を楽しんでください！ 👋

**Pro tip**: もしあなたがあまり IDE を使うことが好みでないとしても Kotlin を使うなら [IntelliJ IDEA](https://www.jetbrains.com/idea/) を使うことを強くおすすめします。この IDE を使うことがもっともスムースな Kotlin アプリ開発の方法です。

---
## 次のステップ

「[Bolt の概要]({{ site.url | append: site.baseurl }}/guides/ja/bolt-basics)」を読んでさらに理解を深めてください。
