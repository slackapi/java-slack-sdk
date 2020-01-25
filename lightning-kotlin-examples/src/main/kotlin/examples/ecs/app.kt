package examples.ecs

import com.slack.api.lightning.App
import com.slack.api.lightning.jetty.SlackAppServer
import com.slack.api.lightning.response.Response
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.command("/echo") { req, ctx ->
        val text = "You said ${req.payload.text} at <#${req.payload.channelId}|${req.payload.channelName}>"
        val res = ctx.respond { it.text(text) }
        logger.info("respond result - {}", res)
        ctx.ack()
    }

    // curl -v http://localhost:3000/
    app.endpoint("/") { _, ctx ->
        ctx.ack()
    }
    // curl -v http://localhost:3000/foo
    app.endpoint("/foo") { _, _ ->
        Response.ok("bar")
    }
    // curl -v -d'hey' http://localhost:3000/bar
    app.endpoint("POST", "/bar") { req, _ ->
        Response.json(200, mapOf("request" to req.requestBodyAsString))
    }

    val server = SlackAppServer(app)
    server.start()
}