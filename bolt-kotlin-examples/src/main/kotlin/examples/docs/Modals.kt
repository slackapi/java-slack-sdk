package examples.docs

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.response.Response
import com.slack.api.model.view.View
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.block.element.BlockElements.*
import com.slack.api.model.view.Views.*

fun main() {
    val app = App(AppConfig.builder()
            .signingSecret("foo")
            .singleTeamBotToken("xoxb-xxx")
            .build())

    app.command("/meeting") { req, ctx ->
        // Build a view using string interpolation
        val commandArg = req.payload.text
        val modalView = """
{
  "type": "modal",
  "callback_id": "meeting-arrangement",
  "notify_on_close": true,
  "title": { "type": "plain_text", "text": "Meeting Arrangement" },
  "submit": { "type": "plain_text", "text": "Submit" },
  "close": { "type": "plain_text", "text": "Cancel" },
  "private_metadata": "${commandArg}"
  "blocks": [
    {
      "type": "input",
      "block_id": "agenda-block",
      "element": { "action_id": "agenda-action", "type": "plain_text_input", "multiline": true },
      "label": { "type": "plain_text", "text": "Detailed Agenda" }
    }
  ]
}
""".trimIndent()
        val res = ctx.client().viewsOpen { it
                .triggerId(ctx.triggerId)
                .viewAsString(modalView)
        }
        if (res.isOk) ctx.ack()
        else Response.builder().statusCode(500).body(res.error).build()
    }

    fun buildViewByCategory(categoryId: String, privateMetadata: String): View? {
        return null // TODO
    }

    app.blockAction("category-selection-action") { req, ctx ->
        val currentView = req.payload.view
        val privateMetadata = currentView.privateMetadata
        val stateValues = currentView.state.values
        val categoryId = stateValues["category-block"]!!["category-selection-action"]!!.selectedOption.value
        val viewForTheCategory = buildViewByCategory(categoryId, privateMetadata)
        val viewsUpdateResp = ctx.client().viewsUpdate { it
                .viewId(currentView.id)
                .hash(currentView.hash)
                .view(viewForTheCategory)
        }
        ctx.ack()
    }

    // when a user clicks "Submit"
    app.viewSubmission("meeting-arrangement") { req, ctx ->
        val privateMetadata = req.payload.view.privateMetadata
        val stateValues = req.payload.view.state.values
        val agenda = stateValues["agenda-block"]!!["agenda-action"]!!.value
        val errors = mutableMapOf<String, String>()
        if (agenda.length <= 10) {
            errors["agenda-block"] = "Agenda needs to be longer than 10 characters."
        }
        if (errors.isNotEmpty()) {
            ctx.ack { it.responseAction("errors").errors(errors) }
        } else {
            // TODO: may store the stateValues and privateMetadata

            // Responding with an empty body means closing the modal now.
            // If your app has next steps, respond with other response_action and a modal view.
            ctx.ack()
        }
    }

    val renewedView: View? = null
    val newViewInStack: View? = null

    app.viewSubmission("meeting-arrangement") { req, ctx ->
        ctx.ack { it.responseAction("update").view(renewedView) }
        ctx.ack { it.responseAction("push").view(newViewInStack) }
    }

    // when a user clicks "Cancel"
    // "notify_on_close": true is required
    app.viewClosed("meeting-arrangement") { req, ctx ->
        // Do some cleanup tasks
        ctx.ack()
    }
}