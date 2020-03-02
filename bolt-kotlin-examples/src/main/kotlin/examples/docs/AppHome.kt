package examples.docs

// static imports
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.view.Views.*

import com.slack.api.model.event.AppHomeOpenedEvent
import java.time.ZonedDateTime

fun main() {
    val app = App(AppConfig.builder()
            .signingSecret("foo")
            .singleTeamBotToken("xoxb-xxx")
            .build())

    // https://api.slack.com/events/app_home_opened
    app.event(AppHomeOpenedEvent::class.java) { event, ctx ->
        // Build a Home tab view
        val now = ZonedDateTime.now()
        val appHomeView = view {
            it.type("home")
                    .blocks(asBlocks(
                            section { section -> section.text(markdownText { mt -> mt.text(":wave: Hello, App Home! (Last updated: ${now})") }) },
                            image { img -> img.imageUrl("https://www.example.com/foo.png") }
                    ))
        }
        // Update the App Home for the given user
        val res = ctx.client().viewsPublish {
            it.userId(event.event.user)
                    .hash(event.event.view?.hash) // To protect against possible race conditions
                    .view(appHomeView)
        }
        ctx.ack()
    }
}