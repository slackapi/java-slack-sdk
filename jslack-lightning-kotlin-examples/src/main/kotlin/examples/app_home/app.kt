package examples.app_home

import com.github.seratch.jslack.api.model.event.AppHomeOpenedEvent
import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    // val app = App()
    val config = util.ResourceLoader.loadAppConfig("appConfig_AppHome.json")
    val app = App(config)

    app.event(AppHomeOpenedEvent::class.java) { e, ctx ->
        val res = ctx.client().viewsPublish { it.token(ctx.botToken)
                .userId(e.event.user)
                .viewAsString(view)
                .hash(e.event.view?.hash)
        }
        logger.info("event - $e / res - $res")
        ctx.ack()
    }
    val server = SlackAppServer(app)
    server.start()
}

val view = """
{
  "type": "home",
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "A simple stack of blocks for the simple sample Block Kit Home tab."
      }
    },
    {
      "type": "actions",
      "elements": [
        {
          "type": "button",
          "text": {
            "type": "plain_text",
            "text": "Action A",
            "emoji": true
          }
        },
        {
          "type": "button",
          "text": {
            "type": "plain_text",
            "text": "Action B",
            "emoji": true
          }
        }
      ]
    }
  ]
}
""".trimIndent()