package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

// https://api.slack.com/tools/block-kit-builder?mode=appHome&template=1
class HomeTemplateTest {

    val gson = GsonFactory.createSnakeCase()

    @Test
    fun projetTracker() {
        val blocks = withBlocks {
            section {
                markdownText("*Here's what you can do with Project Tracker:*")
            }
            actions {
                elements {
                    button {
                        text(text = "Create New Task", emoji = true)
                        style("primary")
                        value("create_task")
                    }
                    button {
                        text("Create New Project")
                        value("create_project")
                    }
                    button {
                        text(text = "Help", emoji = true)
                        value("help")
                    }
                }
            }
            context {
                elements {
                    image(imageUrl = "https://api.slack.com/img/blocks/bkb_template_images/placeholder.png", altText = "placeholder")
                }
            }
            section {
                text(type = "mrkdwn", text = "*Your Configurations*")
            }
            divider()
            section {
                text(type = "mrkdwn", text = "*#public-relations*\n<fakelink.toUrl.com|PR Strategy 2019> posts new tasks, comments, and project updates to <fakelink.toChannel.com|#public-relations>")
                accessory {
                    button {
                        text("Edit", emoji = true)
                        value("public-relations")
                    }
                }
            }
            divider()
            section {
                markdownText("*#team-updates*\n<fakelink.toUrl.com|Q4 Team Projects> posts project updates to <fakelink.toChannel.com|#team-updates>")
                accessory {
                    button {
                        text("Edit", emoji = true)
                        value("public-relations")
                    }
                }
            }
            divider()
            actions {
                elements {
                    button {
                        text("New Configuration", emoji = true)
                        value("new_configuration")
                    }
                }
            }
        }
        val original = """
{
	"type": "home",
	"blocks": [
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Here's what you can do with Project Tracker:*"
			}
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Create New Task",
						"emoji": true
					},
					"style": "primary",
					"value": "create_task"
				},
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Create New Project"
					},
					"value": "create_project"
				},
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Help",
						"emoji": true
					},
					"value": "help"
				}
			]
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "image",
					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/placeholder.png",
					"alt_text": "placeholder"
				}
			]
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Your Configurations*"
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*#public-relations*\n<fakelink.toUrl.com|PR Strategy 2019> posts new tasks, comments, and project updates to <fakelink.toChannel.com|#public-relations>"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Edit",
					"emoji": true
				},
				"value": "public-relations"
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*#team-updates*\n<fakelink.toUrl.com|Q4 Team Projects> posts project updates to <fakelink.toChannel.com|#team-updates>"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Edit",
					"emoji": true
				},
				"value": "public-relations"
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
						"text": "New Configuration",
						"emoji": true
					},
					"value": "new_configuration"
				}
			]
		}
	]
}
        """.trimIndent()
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n" + expected.toString() + "\n" + actual.toString())
    }

}
