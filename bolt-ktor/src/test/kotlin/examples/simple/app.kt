package examples.simple

import com.slack.api.bolt.App
import com.slack.api.bolt.ktor.respond
import com.slack.api.bolt.ktor.toBoltRequest
import com.slack.api.bolt.util.SlackRequestParser
import com.slack.api.model.event.AppMentionEvent
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    // export SLACK_BOT_TOKEN=xoxb-
    // export SLACK_SIGNING_SECRET=
    val app = App()
    val requestParser = SlackRequestParser(app.config())

    app.command("/hello-bolt-python") { req, ctx ->
        if (req.payload.text != null && req.payload.text.isNotEmpty()) {
            ctx.ack("You said: ${req.payload.text}")
        } else {
            ctx.ack()
        }
    }
    app.event(AppMentionEvent::class.java) { _, ctx ->
        ctx.ack()
    }
    app.globalShortcut("test-shortcut") { _, ctx ->
        ctx.ack()
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
