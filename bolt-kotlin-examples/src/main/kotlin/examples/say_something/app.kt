package examples.say_something

import com.slack.api.bolt.App
import com.slack.api.bolt.context.Context
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.methods.request.conversations.ConversationsListRequest
import com.slack.api.model.ConversationType

fun main() {

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    val app = App()

    // requires channels:read scope
    fun toConversationId(ctx: Context, where: String): String? {
        val name = where.replaceFirst("#", "").trim()
        var cursor: String? = null
        var conversationId: String? = null
        while (conversationId == null && cursor != "") {
            val rb =
                    ConversationsListRequest.builder().limit(1000).types(listOf(ConversationType.PUBLIC_CHANNEL))
            val req = if (cursor != null) rb.cursor(cursor).build() else rb.build()
            val list = ctx.client().conversationsList(req)
            for (c in list.channels) {
                if (c.name == name) {
                    conversationId = c.id
                    break
                }
            }
        }
        return conversationId
    }

    // requires channels:join scope
    fun joinConversation(ctx: Context, conversationId: String) {
        val res = ctx.client().conversationsJoin { it.channel(conversationId) }
        ctx.logger.info("conversions.join result - {}", res)
        res
    }

    fun saySomething(ctx: SlashCommandContext, where: String, text: String) {
        val conversationId = toConversationId(ctx, where)
        if (conversationId == null) {
            ctx.ack("[Error] $where was not found")
        } else {
            joinConversation(ctx, conversationId)
            val res = ctx.say { it.channel(where).text(text) }
            ctx.logger.info("say result - {}", res)
        }
    }

    app.command("/say-something") { req, ctx ->
        val elements = req.payload.text?.split(" at ")
        if (elements == null || elements.size < 2) {
            ctx.ack("[Usage] /say-something Hey folks, how have you been doing? at #general")
        } else {
            if (elements.size == 2) {
                val where = elements[1].trim()
                val text = elements[0].trim()
                saySomething(ctx, where, "$text by <@${req.payload.userId}>")
            } else {
                val where = elements[elements.size - 1].trim()
                val text = elements.dropLast(1).joinToString { " at " }.trim()
                saySomething(ctx, where, "$text by <@${req.payload.userId}>")
            }
            ctx.ack()
        }
    }
    val server = SlackAppServer(app)
    server.start()
}
