package examples.simple

import com.slack.api.bolt.App
import com.slack.api.bolt.ktor.respond
import com.slack.api.bolt.ktor.toBoltRequest
import com.slack.api.bolt.util.SlackRequestParser
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    // export SLACK_BOT_TOKEN=xoxb-
    // export SLACK_SIGNING_SECRET=
    val app = App()
    val requestParser = SlackRequestParser(app.config())

    app.command("/hello-ktor") { _, ctx ->
        ctx.ack("Hi!")
    }

    val server = embeddedServer(Netty, port = 3000) {
        routing {
            post("/slack/events") {
                respond(call, app.run(toBoltRequest(call, requestParser)))
            }
        }
    }
    server.start(wait = true)
}
