---
layout: ja
title: "メッセージの組み立て方"
lang: ja
---

# メッセージの組み立て方

このセクションでは **slack-api-client** を使って Slack メッセージを組み立てる方法を解説します。もし、まだ [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) API を使ったことがなければ、このページのサンプルを試す前にまず[こちらのページ]({{ site.url | append: site.baseurl }}/guides/ja/web-api-basics)を読んでください。

また、Java でのコーディングに入る前に「[An overview of message composition（英語）](https://api.slack.com/messaging/composing)」を一読して、Slack メッセージの組み立て方について理解を深めることをおすすめします。

---
## テキストの整形

text でメッセージを作成することが、Slack のチャンネルにメッセージを投稿するための最もシンプルなやり方です。

```java
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

String channelId = "C1234567";
String text = ":wave: こんにちは！これは Java で書いた bot からのメッセージです！";

Slack slack = Slack.getInstance();
String token = System.getenv("SLACK_TOKEN");

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel(channelId)
  .text(text)
);
```

見ての通り、`text` を使うのはとてもシンプルです。知らなければならないことは、正しい形式の文字列を指定する方法だけです。Slack でのマークアップ方式については「[Basic formatting with `mrkdwn`（英語）](https://api.slack.com/reference/surfaces/formatting#basics)」を参照してください。

---
## Block を使ったリッチなレイアウト

[Block Kit](https://api.slack.com/block-kit) は、メッセージやその他の[サーフェスエリア](https://api.slack.com/surfaces)で利用することができる Slack アプリのための UI フレームワークです。Block Kit は優れたバランスで UI の制御と柔軟性を提供します。

特に大きな JSON データ構造になると、それを Java コード内で構築することがやりやすくない場合もあります。そのため、このライブラリでは `blocks` 全体を一つの文字列として渡すことができる `blocksAsString(String)` のようなメソッドも提供しています。このようなメソッドは外部ファイルからの読み込みやテンプレートエンジンとの併用を想定しています。

```java
ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
  .blocksAsString("[{\"type\": \"divider\"}]")
);
```

また、このライブラリでは、以下のような型安全な blocks のビルダーも提供しています。以下のように、いくつかの static import を入れるだけで、blocks の構築はより簡単、安全、誰にとっても可読性が高いものになるでしょう。

```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;

ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
  .channel("C1234567")
  .blocks(asBlocks(
    section(section -> section.text(markdownText("*Please select a restaurant:*"))),
    divider(),
    actions(actions -> actions
      .elements(asElements(
        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Farmhouse"))).value("v1")),
        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Kin Khao"))).value("v2"))
      ))
    )
  ))
);
```

このビルダーは Incoming Webhooks や `response_url` を使った返信の投稿でも利用することができます。

---
## Block Kit Kotlin DSL

もし Kotlin で開発しているなら、この Java SDK は Block Kit の構造を Kotlin らしいやり方で構築できるビルダーモジュールも提供しています。

```kotlin
import com.slack.api.Slack
import com.slack.api.model.block.Blocks.*
import com.slack.api.methods.kotlin_extension.request.chat.blocks

val slack = Slack.getInstance()
val token = System.getenv("token")
val response = slack.methods(token).chatPostMessage { req -> req
  .channel("C1234567")
  .blocks {
    section {
      // "text" フィールドは plainText() や markdownText() を使って構築できます
      markdownText("*Please select a restaurant:*")
    }
    divider()
    actions {
      // JSON の構造と揃えるなら、ここに elements { } のブロックを置くこともできますが、省略しても構いません
      // これは section ブロックの accessory についても同様です
      button {
          // plain_text だけを受け付けている場合は、plain_text 型の入力だけを受け付けます
          text("Farmhouse", emoji = true)
          value("v1")
      }
      button {
          text("Kin Khao", emoji = true)
          value("v2")
      }
    }
  }
}
```

### Kotlin DSL モジュールのインストール

Block Kit Kotlin DSL は、以下の二つの artifact で提供されています:

|artifactId|Description|
|---|---|
|[**com.slack.api:slack-api-model-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-model-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-model-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-model-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|**slack-api-model** の Kotlin 拡張で Kotlin DSL そのものに加えて `withBlocks { }` というどこでも使えるビルダーを提供します。<br/><br/>また `View.ViewBuilder` に `.blocks { }` 拡張関数も追加します。|
|[**com.slack.api:slack-api-client-kotlin-extension**](https://search.maven.org/search?q=g:com.slack.api%20AND%20a:slack-api-client-kotlin-extension) [📖](https://oss.sonatype.org/service/local/repositories/releases/archive/com/slack/api/slack-api-client-kotlin-extension/{{ site.sdkLatestVersion }}/slack-api-client-kotlin-extension-{{ site.sdkLatestVersion }}-javadoc.jar/!/index.html#package)|**slack-api-client** の Kotlin 拡張で `MethodsClient` のリクエストオブジェクトビルダーのメソッドに `.blocks { }` という拡張関数を追加して、シームレスに Kotlin DSL を使えるようにします。<br/><br/>この `.blocks { }` 拡張関数は **ChatPostEphemeralRequestBuilder**, **ChatPostMessageRequestBuilder**, **ChatScheduleMessageRequestBuilder**, **ChatUpdateRequestBuilder** で有効になります。|

**Gradle での設定:**

```groovy
dependencies {
  implementation "com.slack.api:slack-api-model-kotlin-extension:{{ site.sdkLatestVersion }}"
  implementation "com.slack.api:slack-api-client-kotlin-extension:{{ site.sdkLatestVersion }}"
}
```

**Gradle Kotlin DSL での設定:**

```kotlin
dependencies {
  implementation("com.slack.api:slack-api-model-kotlin-extension:{{ site.sdkLatestVersion }}")
  implementation("com.slack.api:slack-api-client-kotlin-extension:{{ site.sdkLatestVersion }}")
}
```

### サンプルと機能の紹介

ここではいくつかサンプルコードとともに DSL の機能を紹介します。

#### どこでも使える `withBlocks { }` ビルダー

**slack-api-client** の Kotlin DSL が提供する拡張関数を使えない場合でも `withBlocks { }` というビルダーの中で、この DSL を使ってブロックのリストを構築することができます。

```kotlin
import com.slack.api.model.kotlin_extension.block.withBlocks

val blocks = withBlocks {
  section {
    plainText("Now this can be passed to anything that requires a list of LayoutBlocks")
  }
}
```

#### 型安全な enum の利用

特定の文字列のいずれかを指定する必要があるようなブロック要素（Block Element）の属性指定に、型安全な enum を利用することができます。

これを利用することでコンパイル時に値が正しいかを検証できる恩恵を受けられますし、全ての値にイテレートしてアクセスしたりといった Kotlin の enum の機能も利用することができます。文字列を指定する方が望ましい場合は、文字列を指定するバージョンのメソッドも提供されています。

```kotlin
import com.slack.api.model.kotlin_extension.block.element.ButtonStyle
import com.slack.api.model.kotlin_extension.block.element.ConversationType
import com.slack.api.model.kotlin_extension.block.withBlocks

val blocks = withBlocks {
  section {
    plainText("Please select the person or group you would like to send a cat GIF to.")

    // "accessory" をここで指定していますが、この階層は省略することもできます
    accessory {
      conversationsSelect {
        // 別のやり方として `filter("im", "mpim")` のように文字列を指定しても OK です
        filter(ConversationType.IM, ConversationType.MULTIPARTY_IM)
        placeholder("Where should we send the cat?")

        confirm {
          title("Confirm destination")
          plainText("Are you sure you want to send a cat GIF to this person or group?")
          confirm("Yes, send it")
          deny("Don't send it")

          style(ButtonStyle.PRIMARY)
        }
      }
    }
  }
}
```

#### DSL の拡張関数を書く

Kotlin DSL であるということは、メッセージを構築する際に Kotlin の言語機能の恩恵を受けられるということです。一つの例として、よく使われるような Block Kit の構造を構築するために、Kotlin の拡張関数を定義することができます。これはコードの繰り返しを減らすためだけでなく、より読みやすいコードにすることも役立ちます。また、ブロックを構築するときに条件式やループを利用したい場合にも有益でしょう。

```kotlin
import com.slack.api.model.kotlin_extension.block.ActionsBlockBuilder
import com.slack.api.model.kotlin_extension.block.withBlocks

fun ActionsBlockBuilder.presentOptions(vararg optionNames: String, prompt: String? = null) {
  staticSelect {
    if (prompt != null) {
      placeholder(prompt)
    }

    options {
      for (optionName in optionNames) {
        option {
          plainText(optionName)
          value(optionName.toLowerCase())
        }
      }
    }
  }
}

val blocks = withBlocks {
  section {
    markdownText("Please select your favorite color.")
  }
  actions {
    presentOptions(
      "Green", "Red", "Blue", "Yellow", "Orange", "Black",
      prompt = "Pick a color..."
    )
  }
}
```
