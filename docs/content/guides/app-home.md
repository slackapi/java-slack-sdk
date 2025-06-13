---
lang: en
---

# App Home

An [App Home](https://docs.slack.dev/surfaces/app-home) is a private, one-to-one space in Slack shared by a user and an app. Each App Home contains a number of tabbed surfaces, including a Messages tab for app-user conversation, and a Home tab that can be fully customized by the app.

### Slack App Configuration

To enable Home tabs, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, go to **Features** > **App Home** on the left pane, and then turn on **Home Tab**.

To enable Events API, go to **Features** > **Event Subscriptions** on the left pane. There are a few things to do on the page.

* Turn on **Enable Events**
* Set the **Request URL** to `https://{your app's public URL domain}/slack/events` (this step is not required for Socket Mode apps)
* Add subscriptions to bot events
  * Click **Subscribe to bot events**
  * Click **Add Bot User Event** button
  * Choose **app_home_opened** event
* Click the **Save Changes** button at the bottom for sure

### What Your Bolt App Does

All your app needs to do to provide Home tabs to your app users are:

1. Call the [**views.publish**](https://docs.slack.dev/reference/methods/views.publish) method to update the Home tab on a per-user basis
2. Handle any user interactions in Home tab (`"block_actions"`, `"block_suggestion"`)

Most commonly, [`"app_home_opened"`](https://docs.slack.dev/reference/events/app_home_opened) events would be used as the trigger to call the [**views.publish**](https://docs.slack.dev/reference/methods/views.publish) method. Subscribing this event type is useful particularly for the initial Home tab creation. But it's also fine to publish Home tabs by any other means.

---
## Examples

:::tip 

If you're a beginner to using Bolt for Slack App development, consult [Getting Started with Bolt](/guides/getting-started-with-bolt), first.

:::

The following code calls [**views.publish**](https://docs.slack.dev/reference/methods/views.publish) method when receiving an [`"app_home_opened"` events](https://docs.slack.dev/reference/events/app_home_opened) for the user that triggered the event. The user will see the updated Home tab immediately after the [**views.publish**](https://docs.slack.dev/reference/methods/views.publish) call has been successfully completed.

```java
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import java.time.ZonedDateTime;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;

// https://docs.slack.dev/reference/events/app_home_opened
app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
  // Build a Home tab view
  ZonedDateTime now = ZonedDateTime.now();
  View appHomeView = view(view -> view
    .type("home")
    .blocks(asBlocks(
      section(section -> section.text(markdownText(mt -> mt.text(":wave: Hello, App Home! (Last updated: " + now + ")")))),
      image(img -> img.imageUrl("https://www.example.com/foo.png").altText("alt text for image"))
    ))
  );
  // Update the App Home for the given user
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

It looks like as below in Kotlin. (New to Kotlin? [Getting Started in Kotlin](/guides/getting-started-with-bolt#getting-started-in-kotlin) may be helpful)

```kotlin
// static imports
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.view.Views.*

import com.slack.api.model.event.AppHomeOpenedEvent
import java.time.ZonedDateTime

// https://docs.slack.dev/reference/events/app_home_opened
app.event(AppHomeOpenedEvent::class.java) { event, ctx ->
  // Build a Home tab view
  val now = ZonedDateTime.now()
  val appHomeView = view {
    it.type("home")
      .blocks(asBlocks(
        section { section -> section.text(markdownText { mt -> mt.text(":wave: Hello, App Home! (Last updated: ${now})") }) },
        image { img -> img.imageUrl("https://www.example.com/foo.png").altText("alt text for image") }
      ))
  }
  // Update the App Home for the given user
  val res = ctx.client().viewsPublish {
    it.userId(event.event.user)
      .hash(event.event.view?.hash) // To protect against possible race conditions 
      .view(appHomeView)
  }
  ctx.ack()
}
```

You can also build the view in the above example with the [Block Kit Kotlin DSL](/guides/composing-messages#block-kit-kotlin-dsl) like so:

```kotlin
// These imports are necessary for this code
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

### Under the Hood

Refer to [the Events API guide](/guides/events-api).