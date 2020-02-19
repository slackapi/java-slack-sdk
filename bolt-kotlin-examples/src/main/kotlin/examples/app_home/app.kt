package examples.app_home

import com.slack.api.app_backend.events.handler.AppHomeOpenedHandler
import com.slack.api.app_backend.events.payload.AppHomeOpenedPayload
import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.model.event.AppHomeOpenedEvent
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime

fun main() {

    val addEventHandler = false

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    // val app = App()
    val config = util.ResourceLoader.loadAppConfig("appConfig_AppHome.json")
    val app = App(config)

    if (addEventHandler) {
        val logger = LoggerFactory.getLogger("main")
        val handler: AppHomeOpenedHandler = object : AppHomeOpenedHandler() {
            override fun handle(payload: AppHomeOpenedPayload?) {
                logger.info("AppHomeOpenedHandler - $payload")
            }
        }
        app.event(handler)
    }

    app.event(AppHomeOpenedEvent::class.java) { e, ctx ->
        val res = ctx.client().viewsPublish {
            it.token(ctx.botToken)
                    .userId(e.event.user)
                    .viewAsString(view())
                    .hash(e.event.view?.hash)
        }
        ctx.logger.info("event - $e / res - $res")
        ctx.ack()
    }
    val server = SlackAppServer(app)
    server.start()
}

fun view(): String {
    return """
{
  "type": "home",
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "A simple stack of blocks for the simple sample Block Kit Home tab. ${ZonedDateTime.now()}"
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
}