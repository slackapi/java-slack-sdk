---
layout: ja
title: "メッセージの組み立て方"
lang: ja
---

# メッセージの組み立て方

このセクションでは **slack-api-client** を使って Slack メッセージを組み立てる方法を解説します。もし、まだ [**chat.postMessage**](https://api.slack.com/methods/chat.postMessage) API を使ったことがなければ、このページのサンプルを試す前にまず[こちらのページ]({{ site.url | append: site.baseurl }}/guides/ja/web-api-basics)を読んでください。

また、Java でのコーディングに入る前に「[An overview of message composition（英語）](https://api.slack.com/messaging/composing)」を一読して、Slack メッセージの組み立て方について理解を深めることをおすすめします。

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

## Block を使ったリッチなレイアウト

[Block Kit](https://api.slack.com/block-kit) は、メッセージやその他の[インターフェース](https://api.slack.com/surfaces)で利用することができる Slack アプリのための UI フレームワークです。Block Kit は優れたバランスで UI の制御と柔軟性を提供します。

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
