package examples.docs

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.model.event.ReactionAddedEvent

fun main() {
    val app = App(AppConfig.builder()
            .signingSecret("foo")
            .singleTeamBotToken("xoxb-xxx")
            .build())

    app.event(ReactionAddedEvent::class.java) { payload, ctx ->
        val event = payload.event
        if (event.reaction == "white_check_mark") {
            val message = ctx.client().chatPostMessage {
                it.channel(event.item.channel)
                        .threadTs(event.item.ts)
                        .text("<@${event.user}> Thank you! We greatly appreciate your efforts :two_hearts:")
            }
            if (!message.isOk) {
                ctx.logger.error("chat.postMessage failed: ${message.error}")
            }
        }
        ctx.ack()
    }
}