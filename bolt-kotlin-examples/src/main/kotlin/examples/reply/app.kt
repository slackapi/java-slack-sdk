package examples.reply

import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.model.block.Blocks.asBlocks
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.model.event.AppMentionEvent

fun main() {

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    app.command("/say-here") { req, ctx ->
        // requires channels:join scope
        ctx.client().conversationsJoin { it.channel(req.payload.channelId) }
        ctx.say(asBlocks(
                section { it.blockId("foo").text(markdownText("This is foo")) }
        ))
        ctx.ack()
    }

    app.event(AppMentionEvent::class.java) { event, ctx ->
        ctx.say("<@${event.event.user}> What's up?")
        ctx.ack()
    }

    val server = SlackAppServer(app)
    server.start()
}
