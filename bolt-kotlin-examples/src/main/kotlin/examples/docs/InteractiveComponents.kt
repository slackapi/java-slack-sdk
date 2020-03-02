package examples.docs

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig

import com.slack.api.app_backend.interactive_components.response.Option
import com.slack.api.model.block.composition.BlockCompositions.plainText // static import
import com.slack.api.model.block.composition.PlainTextObject

fun main() {
    val app = App(AppConfig.builder()
            .signingSecret("foo")
            .singleTeamBotToken("xoxb-xxx")
            .build())

    app.blockAction("button-action") { req, ctx ->
        val value = req.payload.actions[0].value
        if (req.payload.responseUrl != null) {
            ctx.respond("You've sent ${value} by clicking the button!")
        }
        ctx.ack()
    }

    val allOptions = listOf(
            Option(plainText("Schedule", true), "schedule"),
            Option(plainText("Budget", true), "budget"),
            Option(plainText("Assignment", true), "assignment")
    )

    // when a user enters some word in "Topics"
    app.blockSuggestion("topics-action") { req, ctx ->
        val keyword = req.payload.value
        val options = allOptions.filter { (it.text as PlainTextObject).text.contains(keyword) }
        ctx.ack { it.options(if (options.isEmpty()) allOptions else options) }
    }
    // when a user chooses an item from the "Topics"
    app.blockAction("topics-action") { req, ctx ->
        ctx.ack()
    }
}