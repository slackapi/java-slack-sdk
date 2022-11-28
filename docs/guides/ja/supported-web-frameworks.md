---
layout: ja
title: "対応 Web フレームワーク"
lang: ja
---

# 対応 Web フレームワーク

Bolt for Java は特定の環境やフレームワークに依存しません。

標準では Servlet コンテナー上での動作がサポートされています。そのため、開発者は Bolt アプリをほとんどの JVM 上で動作する Web フレームワークと組み合わせて動作させることができます。**SlackAppServlet** は、`POST /slack/events` という URI に来るリクエストを受け付けて、適切に対応する Bolt アプリのハンドラーにディスパッチするだけのシンプルな Servlet です。

[Micronaut](https://micronaut.io/) や [Helidon](https://helidon.io/) のように Servlet ではない環境も、その固有の HTTP 関連の表現（リクエスト・レスポンス）を変換するアダプターさえあれば、Bolt アプリを動作させることができます。

---
## 対応フレームワーク

このセクションでは、最小の動作するサンプルとともに以下の人気フレームワークの対応について説明します。

* [Spring Boot](https://spring.io/guides/gs/spring-boot/)
* [Micronaut](https://micronaut.io/)
* [Quarkus Undertow](https://quarkus.io/)
* [Helidon SE](https://helidon.io/)

---
## Spring Boot

[Spring Boot](https://spring.io/guides/gs/spring-boot/) は Java の世界で最も人気のある Web フレームワークの一つです。**SlackAppServlet** を Spring Boot アプリ内で有効にするのが最も簡単なやり方です。それでは小さな Gradle プロジェクトの例を見てみましょう。**Bolt は Spring Boot 2.2 以上のバージョンで動作しますので、バージョンにご注意ください。**

#### build.gradle

`build.gradle` をプロジェクトのルートディレクトリに配置するところから始めます。Spring Boot 3 を使った例は以下の通りです。見ての通り、わかりやすい普通の Spring Boot アプリのビルド設定です。


```groovy
plugins {
  id 'org.springframework.boot' version '{{ site.springBootVersion }}'
  id 'io.spring.dependency-management' version '1.1.0'
  id 'java'
}
group = 'com.example'
version = '0.0.1-SNAPSHOT'
repositories {
  mavenCentral()
  mavenLocal()
}
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'com.slack.api:bolt-jakarta-servlet:{{ site.sdkLatestVersion }}'
}
```

もし何らかの事情で引き続き Spring Boot 2 を使う必要がある場合、設定は以下の通りとなります。

```groovy
plugins {
  id 'org.springframework.boot' version '2.7.5'
  id 'io.spring.dependency-management' version '1.1.0'
  id 'java'
}
group = 'com.example'
version = '0.0.1-SNAPSHOT'
repositories {
  mavenCentral()
  mavenLocal()
}
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'com.squareup.okhttp3:okhttp:{{ site.okhttpVersion }}'
  implementation 'com.slack.api:bolt-servlet:{{ site.sdkLatestVersion }}'
}
```

#### src/main/java/hello/SlackApp.java

このファイルが、このアプリの本質的な部分です。全ての Slack イベントを扱うロジックはここにあります。この `@Configuration` クラスでは、Spring DI コンテナーをフル活用して、他のサービスクラスやどんなコンポーネントも **App** に注入することができます。

```java
package hello;

import com.slack.api.bolt.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {
  @Bean
  public App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### src/main/java/hello/SlackAppController.java

これはある種のボイラープレート的なコードです。リクエストを受け付けるパスは `@WebServlet` アノテーションの引数で変更できます。

```java
package hello;

import com.slack.api.bolt.App;

import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;
import jakarta.servlet.annotation.WebServlet;

// Spring Boot 2 では import 部分が少し異なります
// import com.slack.api.bolt.servlet.SlackAppServlet;
// import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
  public SlackAppController(App app) {
    super(app);
  }
}
```

#### src/main/java/hello/Application.java

これもボイラープレートです。このコードはただ Spring のコンポーネントスキャンの有効化と Spring Boot アプリの起動を行います。

```java
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

もしあなたのアプリが独自の `ServletContextListener` を持つ予定なら、上記の Servlet クラスを特定のパッケージに配置し、`@ServletComponentScan` の引数にスキャンするパッケージを明に指定することをお勧めします。詳細は [#947](https://github.com/slackapi/java-slack-sdk/issues/947) を参考にしてください。

#### src/main/resources/application.yml

最後に、リソースディレクトリに設定ファイルを配置します。以下の例は **Slack SDK for Java** 関連のログレベルのみを DEBUG に変更し、起動時にリッスンするポートをデフォルトの 8080 から 3000 に変更しています。

```yaml
logging.level:
  com.slack.api: DEBUG
server:
  port: 3000
```

### Bolt アプリを Boot してみよう

準備は以上です！それでは `gradle bootRun` でアプリを起動してみましょう。


```
$ gradle bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v{{ site.springBootVersion }})

[main] hello.Application                        : Starting Application on MACHNE_NAME with PID 7815 (/path-to-project/build/classes/java/main started by seratch in /path-to-project)
[main] hello.Application                        : No active profile set, falling back to default profiles: default
[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 3000 (http)
[main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.29]
[main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
[main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 478 ms
[main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 3000 (http) with context path ''
[main] hello.Application                        : Started Application in 1.079 seconds (JVM running for 1.301)
<=========----> 75% EXECUTING [17s]
> :bootRun
```

そして、このアプリへと Slack API からのリクエストをフォーワードするようにします。

```bash
ngrok http 3000 --subdomain {あなたのサブドメイン}
```

---
## Micronaut

ありふれた Servlet での実行環境ではなく、[Micronaut](https://micronaut.io/) を使いたい場合、**bolt-micronaut** というライブラリだけを追加します（**bolt** ライブラリもこれの依存ライブラリとして自動で解決されます）。**bolt-jetty** は必要ないので注意してください。以下は Maven の例ですが、もちろん Gradle でも同様です。

```xml
<!-- Micronaut {{ site.compatibleMicronautVersion }} と互換性があります -->
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-micronaut</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
```

#### src/main/java/hello/Application.java

**Application.java** はボイラープレートです。この内容をそのまま貼り付けるだけで OK です。

```java
package hello;

import io.micronaut.runtime.Micronaut;

public class Application {
  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
```

#### src/main/java/hello/AppFactory.java

最もシンプルなやり方は **App** インスタンスを `@Factory` なクラスの中で初期化するやり方です。Micronaut は DI 関連のアノテーションがつけられたクラスを自動でスキャンして、それらをコンポーネントの注入をするときに利用します。

```java
package hello;

import com.slack.api.bolt.App;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class AppFactory {

  @Singleton
  public AppConfig createAppConfig() {
    return new AppConfig(); // 環境変数が設定されている前提
  }

  @Singleton
  public App createApp(AppConfig config) {
    App app = new App(config);
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### src/main/resources/application.yml

リッスンするポートを 3000 のようにデフォルトとは違うものに変更するには、以下のような設定ファイルを配置します。

```yaml
---
micronaut:
  application:
    name: micronaut-slack-app
  server:
    port: 3000
```

### Micronaut アプリを起動

準備は以上です！それでは `mvn run` でアプリを起動してみましょう。

```
[main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 1321ms. Server Running: http://localhost:3000
```

---
## Quarkus Undertow

[Quarkus](https://quarkus.io/) は GraalVM と HotSpot 両方へのパッケージングをサポートしている Web アプリケーションフレームワークです。このセクションでは、どのように **SlackAppServlet** をこのフレームワークを使って設定するかを説明します。

[code.quarkus.io](https://code.quarkus.io/) からブランクプロジェクトを生成できます。シンプルな Bolt アプリであれば **Web** コンポーネント内で **Undertow Servlet** を選択することをおすすめします。それ以外には何も必要ありません。**Generate your application** ボタンをクリックして、生成されたプロジェクトの zip ファイルをダウンロードします。

また、もし RESTEasy を使って追加のエンドポイントを実装しないなら、デフォルトで追加されている **quarkus-resteasy** も外して問題ありません。

### ビルド設定

以下のビルド設定ファイルは、そのままコピーして利用することができます。Maven プロジェクトでは `pom.xml` を、Gradle プロジェクトでは `build.gradle` と `settings.gradle` をプロジェクトのルートディレクトリに配置してください。

#### Maven - pom.xml

以下の設定は JDK 8 と 11 両方に互換性があります。もし Java 11 機能を利用したい場合は `maven.compiler.source` と `maven.compiler.target` に `11` を設定してください。

```xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.acme</groupId>
    <artifactId>code-with-quarkus</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <properties>
        <compiler-plugin.version>3.8.1</compiler-plugin.version>
        <failsafe.useModulePath>false</failsafe.useModulePath>
        <maven.compiler.release>11</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
        <quarkus.platform.group-id>io.quarkus.platform</quarkus.platform.group-id>
        <quarkus.platform.version>{{ site.quarkusVersion }}</quarkus.platform.version>
        <slack-sdk.version>{{ site.sdkLatestVersion }}</slack-sdk.version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>${quarkus.platform.artifact-id}</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.slack.api</groupId>
            <artifactId>bolt</artifactId>
            <version>${slack-sdk.version}</version>
        </dependency>
        <dependency>
            <groupId>com.slack.api</groupId>
            <artifactId>bolt-servlet</artifactId>
            <version>${slack-sdk.version}</version>
        </dependency>

        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-arc</artifactId>
            <version>${quarkus.platform.version}</version>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-core</artifactId>
            <version>${quarkus.platform.version}</version>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-undertow</artifactId>
            <version>${quarkus.platform.version}</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>quarkus-maven-plugin</artifactId>
                <version>${quarkus.platform.version}</version>
                <extensions>true</extensions>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                            <goal>generate-code</goal>
                            <goal>generate-code-tests</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                    <compilerArgs>
                        <arg>-parameters</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

#### Gradle - 1) build.gradle

Gradle プロジェクトでは最低でもこれらの二つのファイルが必要です。

```groovy
plugins {
  id 'java'
  id 'io.quarkus'
}
repositories {
  mavenCentral()
}
dependencies {
  implementation 'io.quarkus:quarkus-undertow:{{ site.quarkusVersion }}'
  implementation 'com.slack.api:bolt-servlet:{{ site.sdkLatestVersion }}'
}
group 'org.acme'
version '1.0.0-SNAPSHOT'
```

#### Gradle - 2) settings.gradle

```groovy
pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
  plugins {
    id 'io.quarkus' version "{{ site.quarkusVersion }}"
  }
}
rootProject.name='code-with-quarkus'
```

### src/main/java/hello/SlackApp.java

たった一つだけやらなければいけないことは `@WebServlet` を指定したクラスを定義することです。Quarkus はこのようなクラスを自動でスキャンして適切に有効化してくれます。

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackApp extends SlackAppServlet {
  private static final long serialVersionUID = 1L;
  public SlackApp() { super(initSlackApp()); }
  public SlackApp(App app) { super(app); }

  private static App initSlackApp() {
    App app = new App();
    app.command("/hello", (req, ctx) -> {
      return ctx.ack("What's up?");
    });
    return app;
  }
}
```

#### src/main/kotlin/hello/SlackApp.java

プロジェクトを生成するときに言語として Kotlin を選んだ場合、コードはこのようになります。

```kotlin
package hello

import com.slack.api.bolt.App
import com.slack.api.bolt.servlet.SlackAppServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/slack/events")
class SlackApp : SlackAppServlet(initSlackApp()) {

  companion object {
    fun initSlackApp(): App {
      val app = App()
      app.command("/ping") { req, ctx ->
        ctx.ack("<@${req.payload.userId}> pong!")
      }
      return app
    }
  }
}
```

#### src/main/kotlin/app.kt

適切に DI を活用するなら `@Produces` アノテーションを指定したクラスを定義する方がよいでしょう。

```kotlin
package hello

import com.slack.api.bolt.App
import com.slack.api.bolt.servlet.SlackAppServlet
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.servlet.annotation.WebServlet

@WebServlet("/slack/events")
class SlackApp(app: App?) : @Inject SlackAppServlet(app)

class Components {
  @Produces
  fun initApp(): App {
    val app = App()
    app.command("/ping") { req, ctx ->
      ctx.ack("<@${req.payload.userId}> pong!")
    }
    return app
  }
}
```

### src/main/resources/application.properties

Quarkus がデフォルトで使用するポートは 8080 です。以下の設定で変更することができます。Bolt のデバッグログを有効にすると Bolt がどのように動作するかを学んだり、意図しなかった挙動をしたときのデバッグするときに大変役立つでしょう。

```
quarkus.http.port=3000
quarkus.log.level=INFO
quarkus.log.category."com.slack.api".level=DEBUG
quarkus.package.type=uber-jar
```

### Quarkus アプリを起動

準備は以上です！それでは development mode でアプリを起動してみましょう。


```bash
./mvnw quarkus:dev
./mvnw clean quarkus:dev # 変更が反映されないときは clean を試してみてください
```

```bash
./gradlew quarkusDev
```

プロジェクトが適切に設定されていれば、標準出力はこのようになっているでしょう。

```
[INFO] --- quarkus-maven-plugin:{{ site.quarkusVersion }}:dev (default-cli) @ code-with-quarkus ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /path-to-projet/target/classes
Listening for transport dt_socket at address: 5005
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (main) code-with-quarkus 1.0.0-SNAPSHOT (powered by Quarkus {{ site.quarkusVersion }})) started in 0.846s. Listening on: http://0.0.0.0:3000
INFO  [io.quarkus] (main) Profile dev activated. Live Coding activated.
INFO  [io.quarkus] (main) Installed features: [cdi, servlet]
```

ホットリロードのモードもこのようにデフォルトで有効になっているはずです。

```
INFO  [io.qua.dev] (vert.x-worker-thread-0) Changed source files detected, recompiling [/path-to-project/src/main/java/hello/SlackApp.java]
INFO  [io.quarkus] (vert.x-worker-thread-0) Quarkus stopped in 0.001s
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
INFO  [io.quarkus] (vert.x-worker-thread-0) code-with-quarkus 1.0.0-SNAPSHOT (powered by Quarkus {{ site.quarkusVersion }}) started in 0.021s. Listening on: http://0.0.0.0:3000
INFO  [io.quarkus] (vert.x-worker-thread-0) Profile dev activated. Live Coding activated.
INFO  [io.quarkus] (vert.x-worker-thread-0) Installed features: [cdi, servlet]
INFO  [io.qua.dev] (vert.x-worker-thread-0) Hot replace total time: 0.232s 
```

本番環境にデプロイするために実行可能な jar ファイルをつくるには、`./mvnw package` か `./gradlew package` を実行してください。

---
## Helidon SE

[Helidon SE](https://helidon.io/docs/latest/#/about/02_introduction) は Helidon プロジェクトのライブラリ群によって提供される関数型プログラミングの Web フレームワークです。早速ブランクプロジェクトからはじめてみましょう。
 
```bash
mvn archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeGroupId=io.helidon.archetypes \
  -DarchetypeArtifactId=helidon-quickstart-se \
  -DarchetypeVersion={{ site.helidonVersion }} \
  -DgroupId=com.exmple \
  -DartifactId=helidon-se-bolt-app \
  -Dpackage=hello
```

### pom.xml

ビルド設定でやらなければならないのは **bolt-helidon** ライブラリとお好きな [SLF4J](http://www.slf4j.org/) 実装ライブラリの追加だけです。

```xml
<dependency>
  <groupId>io.helidon.bundles</groupId>
  <artifactId>helidon-bundles-webserver</artifactId>
</dependency>
<dependency>
  <groupId>io.helidon.config</groupId>
  <artifactId>helidon-config-yaml</artifactId>
</dependency>
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-helidon</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
</dependency>
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.3</version>
</dependency>
```

### src/main/java/hello/Main.java

**bolt-helidon** は **bolt-jetty** と同様にとても手軽に扱えます。開発者はただ **App** の初期化と HTTP サーバーを起動する main メソッドを定義するだけです。

```java
package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.helidon.SlackAppServer;
import com.slack.api.model.event.AppMentionEvent;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.metrics.MetricsSupport;

public final class Main {
  public static void main(final String[] args) { startServer(); }

  public static SlackAppServer startServer() {
    SlackAppServer server = new SlackAppServer(apiApp());
    // 他の設定を Routing に加えたい時はこの関数で設定します 
    server.setAdditionalRoutingConfigurator(builder -> builder
      .register(MetricsSupport.create())
      .register(HealthSupport.builder().addLiveness(HealthChecks.healthChecks()).build()));
    server.start();
    return server;
  }

  // POST /slack/events - このパスは application.yaml 内の bolt.apiPath で設定します
  public static App apiApp() {
    App app = new App();
    app.event(AppMentionEvent.class, (event, ctx) -> {
      ctx.say("何かご用でしょうか？ :eyes:");
      return ctx.ack();
    });
    return app;
  }
}
```

### src/main/resources/application.yml

Helidon SE アプリの設定は `application.yml` で行います。

```yaml
server:
  port: 3000
  host: 0.0.0.0
bolt:
  apiPath: /slack/events
```

### src/main/resources/logback.xml

もし SLF4J の実装として logback を使っていれば、最低限のシンプルな **logback.xml** はこのようになります。

```xml
<configuration>
  <appender name="default" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%date %level [%thread] %logger{64} %msg%n</pattern>
    </encoder>
  </appender>
  <logger name="com.slack.api" level="debug"/>
  <logger name="io.helidon" level="debug"/>
  <root level="info">
    <appender-ref ref="default"/>
  </root>
</configuration>
```

### Helidon アプリを起動

推奨される起動方法は [Helidon CLI](https://helidon.io/docs/latest/#/about/05_cli) の dev mode を使うか、

```bash
helidon dev
```

変更する度にアプリケーションをビルドして起動し直すやり方です。

```bash
mvn exec:java -Dexec.mainClass="hello.Main"
# or
mvn package && java -jar target/helidon-se-bolt-app.jar
```

プロジェクトが正しく設定されていれば、以下のように表示されているはずです。

```bash
[main] io.helidon.webserver.NettyWebServer Version: {{ site.helidonVersion }}
[nioEventLoopGroup-2-1] io.helidon.webserver.NettyWebServer Channel '@default' started: [id: 0x9fcf416d, L:/0:0:0:0:0:0:0:0:3000]
[nioEventLoopGroup-2-1] com.slack.api.bolt.helidon.SlackAppServer ⚡️ Bolt app is running!
```
