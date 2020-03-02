package examples.docs

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.request.builtin.SlashCommandRequest

fun main() {
    val app = App(AppConfig.builder()
            .signingSecret("foo")
            .singleTeamBotToken("xoxb-xxx")
            .build())

    app.command("/echo") { req, ctx ->
        val commandArgText = req.payload.text
        val channelId = req.payload.channelId
        val channelName = req.payload.channelName
        val text = "You said ${commandArgText} at <#${channelId}|${channelName}>"
        ctx.ack(text)
    }

    fun buildMessage(req: SlashCommandRequest): String? {
        return null
    }

    app.command("/echo") { req, ctx ->
        val text = buildMessage(req)
        ctx.respond(text) // send via response_url
        ctx.ack()
    }
}