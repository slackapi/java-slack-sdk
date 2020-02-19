package examples.meeting_arrangement_modals

import com.slack.api.app_backend.interactive_components.response.Option
import com.slack.api.bolt.App
import com.slack.api.bolt.jetty.SlackAppServer
import com.slack.api.bolt.response.Response
import com.slack.api.model.block.composition.PlainTextObject
import org.slf4j.LoggerFactory
import util.ResourceLoader

fun main() {

    val logger = LoggerFactory.getLogger("main")

    // export SLACK_BOT_TOKEN=xoxb-***
    // export SLACK_SIGNING_SECRET=123abc***
    //val app = App()
    val app = App(ResourceLoader.loadAppConfig("appConfig_MeetingArrangement.json"))

    app.use { req, _, chain ->
        logger.info("Request - $req")
        val resp = chain.next(req)
        logger.info("Response - $resp")
        resp
    }

    app.command("/meeting") { _, ctx ->
        val res = ctx.client().viewsOpen { it.triggerId(ctx.triggerId).viewAsString(view) }
        logger.info("views.open result - {}", res)
        if (res.isOk) ctx.ack()
        else Response.builder().statusCode(500).body(res.error).build()
    }

    // when a user enters some word in "Topics"
    app.blockSuggestion("topics-input") { req, ctx ->
        val keyword = req.payload.value
        val options = allOptions.filter { (it.text as PlainTextObject).text.contains(keyword) }
        ctx.ack { it.options(if (options.isEmpty()) allOptions else options) }
    }
    // when a user chooses an item from the "Topics"
    app.blockAction("topics-input") { _, ctx -> ctx.ack() }

    // when a user clicks "Submit"
    app.viewSubmission("meeting-arrangement") { req, ctx ->
        val stateValues = req.payload.view.state.values
        val agenda = stateValues["agenda"]!!["agenda-input"]!!.value
        val errors = mutableMapOf<String, String>()
        if (agenda.length <= 10) {
            errors["agenda"] = "Agenda needs to be longer than 10 characters."
        }
        if (errors.isNotEmpty()) {
            ctx.ack { it.responseAction("errors").errors(errors) }
        } else {
            // Operate something with the data
            logger.info("state: $stateValues private_metadata: ${req.payload.view.privateMetadata}")
            ctx.ack()
        }
    }

    // when a user clicks "Cancel"
    app.viewClosed("meeting-arrangement") { _, ctx ->
        ctx.ack()
    }

    val server = SlackAppServer(app)
    server.start()
}

val allOptions = listOf(
        Option(PlainTextObject("Schedule", true), "schedule"),
        Option(PlainTextObject("Budget", true), "budget"),
        Option(PlainTextObject("Assignment", true), "assignment")
)

val view = """
{
  "callback_id": "meeting-arrangement",
  "type": "modal",
  "notify_on_close": true,
  "title": {
    "type": "plain_text",
    "text": "Meeting Arrangement",
    "emoji": true
  },
  "submit": {
    "type": "plain_text",
    "text": "Submit",
    "emoji": true
  },
  "close": {
    "type": "plain_text",
    "text": "Cancel",
    "emoji": true
  },
  "blocks": [
    {
      "block_id": "date",
      "type": "input",
      "element": {
        "action_id": "date-input",
        "type": "datepicker",
        "initial_date": "2019-10-22",
        "placeholder": {
          "type": "plain_text",
          "text": "Select a date",
          "emoji": true
        }
      },
      "label": {
        "type": "plain_text",
        "text": "Meeting Date",
        "emoji": true
      }
    },
    {
      "block_id": "topics",
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "Select the meeting topics"
      },
      "accessory": {
        "action_id": "topics-input",
        "type": "multi_external_select",
        "min_query_length": 1,
        "placeholder": {
          "type": "plain_text",
          "text": "Select",
          "emoji": true
        }
      }
    },
    {
      "block_id": "agenda",
      "type": "input",
      "element": {
        "action_id": "agenda-input",
        "type": "plain_text_input",
        "multiline": true
      },
      "label": {
        "type": "plain_text",
        "text": "Detailed Agenda",
        "emoji": true
      }
    }
  ]
}
""".trimIndent()