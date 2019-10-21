package example

import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.command("/hi-google-cloud-run") { req, ctx ->
        val text = "You said ${req.payload.text} at <#${req.payload.channelId}|${req.payload.channelName}>"
        val res = ctx.respond { it.text(text) }
        logger.info("respond result - {}", res)
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
    val server = SlackAppServer(app, "/slack/events", port)
    server.start()
}