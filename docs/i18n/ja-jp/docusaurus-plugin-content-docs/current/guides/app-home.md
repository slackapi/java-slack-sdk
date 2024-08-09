---
lang: ja
---

# Home タブ

[App Home](https://api.slack.com/surfaces/tabs/events) は、Slack の中でもユーザーにとってプライベートな、アプリと 1 対 1 で共有しているスペースです。各アプリの App Home は複数のタブを持つことができ、アプリのボットユーザーとの会話の履歴である Messages タブ、アプリによってユーザーひとりひとりにそれぞれ個別にカスタマイズされている Home タブがあります。

### Slack アプリの設定

Home タブを有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **App Home** へ遷移します。画面を少し下スクロールすると見つかる **Home Tab** を有効化します。

イベント API を有効にするには、左ペインの **Features** > **Event Subscriptions** へ遷移します。この画面でいくつかやることがあります。

* **Enable Events** を Off から On にする
* `https://{あなたのドメイン}/slack/events` を **Request URL** に設定 (ソケットモードの場合、この手順は不要です)
* Bot User Event を設定
  * **Subscribe to bot events** をクリック
  * **Add Bot User Event** ボタンをクリック
  * **app_home_opened** を選択
* 最下部にある **Save Changes** ボタンをクリック

### Bolt アプリがやること

Bolt アプリが Home タブの提供のためにやらなければならないことは以下の通りです。

1. ユーザごとに Home タブを更新するために [**views.publish**](https://api.slack.com/methods/views.publish) API メソッドを呼び出す
2. Home タブ上で発生したユーザーインタラクション (`"block_actions"`, `"block_suggestion"`) をハンドリング

よくある手法としては [`"app_home_opened"`](https://api.slack.com/events/app_home_opened) のイベントを使って [**views.publish**](https://api.slack.com/methods/views.publish) API メソッドを呼び出すきっかけに使うという実装があります。特に初回更新にはこのイベントの利用が必要です。しかし、それ以外のタイミング・手段で Home タブを更新することも全く問題ありません。

---
## コード例

**注**: もし Bolt を使った Slack アプリ開発にまだ慣れていない方は、まず「[Bolt 入門](/guides/getting-started-with-bolt)」を読んでください。

以下のコードは、ユーザーが App Home にアクセスして [`"app_home_opened"` イベント](https://api.slack.com/events/app_home_opened)が発火したときに [**views.publish**](https://api.slack.com/methods/views.publish) API メソッドによって Home タブを更新しています。[**views.publish**](https://api.slack.com/methods/views.publish) の呼び出しが成功すれば、即座に Home タブの変更が反映されます。

```java
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import java.time.ZonedDateTime;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;

// https://api.slack.com/events/app_home_opened
app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
  // Home タブの view を構成
  ZonedDateTime now = ZonedDateTime.now();
  View appHomeView = view(view -> view
    .type("home")
    .blocks(asBlocks(
      section(section -> section.text(markdownText(mt -> mt.text(":wave: ようこそ！ (最終更新日時: " + now + ")")))),
      image(img -> img.imageUrl("https://www.example.com/foo.png").altText("alt text for image"))
    ))
  );
  // Home タブをこのユーザーのために更新
  if (payload.getEvent().getView() == null) {
    ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
      .userId(payload.getEvent().getUser())
      .view(appHomeView)
    );
  } else {
    ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
      .userId(payload.getEvent().getUser())
      .hash(payload.getEvent().getView().getHash()) // To safeguard against potential race conditions
      .view(appHomeView)
    );
  }
  return ctx.ack();
});
```

Kotlin で書いた同じコードは以下のようになります（参考：「[Bolt 入門 > Kotlin での設定](/guides/getting-started-with-bolt#getting-started-in-kotlin)」）。

```kotlin
// static imports
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.view.Views.*

import com.slack.api.model.event.AppHomeOpenedEvent
import java.time.ZonedDateTime

// https://api.slack.com/events/app_home_opened
app.event(AppHomeOpenedEvent::class.java) { event, ctx ->
  // Home タブの view を構成
  val now = ZonedDateTime.now()
  val appHomeView = view {
    it.type("home")
      .blocks(asBlocks(
        section { section -> section.text(markdownText { mt -> mt.text(":wave: ようこそ！ (最終更新日時: ${now})") }) },
        image { img -> img.imageUrl("https://www.example.com/foo.png").altText("alt text for image") }
      ))
  }
  // Home タブをこのユーザーのために更新
  val res = ctx.client().viewsPublish {
    it.userId(event.event.user)
      .hash(event.event.view?.hash) // レースコンディション防止のため 
      .view(appHomeView)
  }
  ctx.ack()
}
```

また、 Kotlin で開発しているなら、上記の例を [Block Kit Kotlin DSL](/guides/composing-messages#block-kit-kotlin-dsl) を使って以下のように実装することもできます。

```kotlin
// これらの import が必要です
import com.slack.api.model.kotlin_extension.view.blocks
import com.slack.api.model.view.Views.view

val appHomeView = view { it
  .type("home")
  .blocks {
    section {
      markdownText(":wave: Hello, App Home! (Last updated: ${now}")
    }
    image {
      imageUrl("https://www.example.com/foo.png")
    }
  }
}
```

### Bolt がやっていること

「[イベント API](/guides/events-api)」の同項目を参照してください。
