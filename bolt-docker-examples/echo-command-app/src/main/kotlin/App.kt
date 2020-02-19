package example

import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer

fun main() {

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.command("/echo") { req, ctx ->
        val text = "You said ${req.payload.text} at <#${req.payload.channelId}|${req.payload.channelName}>"
        val res = ctx.respond { it.text(text) }
        ctx.logger.info("respond result - {}", res)
        ctx.ack()
    }

    // Amazon Elastic Container Service - the default health check endpoint
    // [ "CMD-SHELL", "curl -f http://localhost/ || exit 1" ]
    // https://docs.aws.amazon.com/AmazonECS/latest/APIReference/API_HealthCheck.html
    app.endpoint("/") { _, ctx ->
        ctx.ack()
    }

    // export SLACK_PORT=8080
    val envPort: String? = System.getenv()["SLACK_PORT"]
    val port: Int = if (envPort == null) 8080 else Integer.valueOf(envPort)
    val server = SlackAppServer(app, port)
    server.start()
}