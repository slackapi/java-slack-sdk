---
layout: ja
title: "Bolt for Java️"
lang: ja
---

# Bolt️ での開発

Bolt️ は Java やその他の JVM 言語を使って、Slack アプリを早く・安全に作るための最適なフレームワークです。

```xml
<dependency>
  <groupId>com.slack.api</groupId>
  <artifactId>bolt-jetty</artifactId>
  <version>{{ site.sdkLatestVersion }}</version>
  <scope>compile</scope>
</dependency>
```

Here is a tiny Slack app that nicely responds to `/echo` slash command.

```java
package example;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class SlackApp {
  public static void main(String[] args) throws Exception {
    App app = new App();
    app.command("/echo", (req, ctx) -> {
      return ctx.ack(r -> r.text(req.getPayload().getText()));
    });
    SlackAppServer server = new SlackAppServer(app, 3000);
    server.start();
  }
}
```

You can easily run the app:

```bash
export SLACK_BOT_TOKEN=xoxb-***
export SLACK_SIGNING_SECRET=123abc***
mvn compile exec:java -Dexec.mainClass="example.SlackApp"
```

If you're familiar with Kotlin language, the code can be much simpler as below!

```kotlin
import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer

fun main() {
  val app = App()
  app.command("/echo") { req, ctx ->
    val text = "You said ${req.payload.text} at <#${req.payload.channelId}|${req.payload.channelName}>"
    ctx.ack { it.text(text) }
  }
  val server = SlackAppServer(app, 3000)
  server.start()
}
```

