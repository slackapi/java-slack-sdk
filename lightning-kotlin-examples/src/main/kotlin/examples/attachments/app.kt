package examples.attachments

import com.slack.api.app_backend.interactive_components.response.ActionResponse
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse
import com.github.seratch.jslack.lightning.App
import com.github.seratch.jslack.lightning.jetty.SlackAppServer
import com.github.seratch.jslack.lightning.util.JsonOps
import org.slf4j.LoggerFactory
import util.ResourceLoader

fun main() {
    val logger = LoggerFactory.getLogger("main")

    val config = ResourceLoader.loadAppConfig()
    val app = App(config)

    app.command("/say-something") { _, ctx ->
        ctx.respond(JsonOps.fromJson(firstMessage, SlashCommandResponse::class.java))
        ctx.ack()
    }
    app.attachmentAction("wopr_game") { req, ctx ->
        logger.info("attachment action - {}, {}", req.payload, ctx)
        ctx.respond(JsonOps.fromJson(secondMessage, ActionResponse::class.java))
        ctx.ack()
    }

    val server = SlackAppServer(app)
    server.start()
}

val firstMessage = """
{
  "text": "Would you like to play a game?",
  "attachments": [
    {
      "text": "Choose a game to play",
      "fallback": "You are unable to choose a game",
      "callback_id": "wopr_game",
      "color": "#3AA3E3",
      "attachment_type": "default",
      "actions": [
        {
          "name": "game",
          "text": "Chess",
          "type": "button",
          "value": "chess"
        },
        {
          "name": "game",
          "text": "Falken's Maze",
          "type": "button",
          "value": "maze"
        },
        {
          "name": "game",
          "text": "Thermonuclear War",
          "style": "danger",
          "type": "button",
          "value": "war",
          "confirm": {
            "title": "Are you sure?",
            "text": "Wouldn't you prefer a good game of chess?",
            "ok_text": "Yes",
            "dismiss_text": "No"
          }
        }
      ]
    }
  ]
}
    """.trimIndent()

val secondMessage = """
{
  "replace_original": true,
  "text": "test",
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\n\n *Please select a restaurant:*"
      }
    },
    {
      "type": "divider"
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*Farmhouse Thai Cuisine*\n:star::star::star::star: 1528 reviews\n They do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here"
      },
      "accessory": {
        "type": "image",
        "image_url": "https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg",
        "alt_text": "alt text for image"
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*Kin Khao*\n:star::star::star::star: 1638 reviews\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft."
      },
      "accessory": {
        "type": "image",
        "image_url": "https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg",
        "alt_text": "alt text for image"
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder."
      },
      "accessory": {
        "type": "image",
        "image_url": "https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg",
        "alt_text": "alt text for image"
      }
    },
    {
      "type": "divider"
    },
    {
      "type": "actions",
      "elements": [
        {
          "type": "button",
          "text": {
            "type": "plain_text",
            "text": "Farmhouse",
            "emoji": true
          },
          "value": "click_me_123"
        },
        {
          "type": "button",
          "text": {
            "type": "plain_text",
            "text": "Kin Khao",
            "emoji": true
          },
          "value": "click_me_123"
        },
        {
          "type": "button",
          "text": {
            "type": "plain_text",
            "text": "Ler Ros",
            "emoji": true
          },
          "value": "click_me_123"
        }
      ]
    }
  ]
}
    """.trimIndent()
