package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

// https://api.slack.com/tools/block-kit-builder?mode=message&template=1
class MessageTemplateTest {

    val gson = GsonFactory.createSnakeCase()

    @Test
    fun approval() {
        // https://api.slack.com/tools/block-kit-builder?mode=message&blocks=%5B%7B%22type%22%3A%22section%22%2C%22text%22%3A%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22You%20have%20a%20new%20request%3A%5Cn*%3CfakeLink.toEmployeeProfile.com%7CFred%20Enriquez%20-%20New%20device%20request%3E*%22%7D%7D%2C%7B%22type%22%3A%22section%22%2C%22fields%22%3A%5B%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22*Type%3A*%5CnComputer%20(laptop)%22%7D%2C%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22*When%3A*%5CnSubmitted%20Aut%2010%22%7D%2C%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22*Last%20Update%3A*%5CnMar%2010%2C%202015%20(3%20years%2C%205%20months)%22%7D%2C%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22*Reason%3A*%5CnAll%20vowel%20keys%20aren%27t%20working.%22%7D%2C%7B%22type%22%3A%22mrkdwn%22%2C%22text%22%3A%22*Specs%3A*%5Cn%5C%22Cheetah%20Pro%2015%5C%22%20-%20Fast%2C%20really%20fast%5C%22%22%7D%5D%7D%2C%7B%22type%22%3A%22actions%22%2C%22elements%22%3A%5B%7B%22type%22%3A%22button%22%2C%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22emoji%22%3Atrue%2C%22text%22%3A%22Approve%22%7D%2C%22style%22%3A%22primary%22%2C%22value%22%3A%22click_me_123%22%7D%2C%7B%22type%22%3A%22button%22%2C%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22emoji%22%3Atrue%2C%22text%22%3A%22Deny%22%7D%2C%22style%22%3A%22danger%22%2C%22value%22%3A%22click_me_123%22%7D%5D%7D%5D
        val blocks = withBlocks {
            section {
                markdownText("You have a new request:\n*<fakeLink.toEmployeeProfile.com|Fred Enriquez - New device request>*")
            }
            section {
                fields {
                    markdownText("*Type:*\nComputer (laptop)")
                    markdownText("*When:*\nSubmitted Aut 10")
                    markdownText("*Last Update:*\nMar 10, 2015 (3 years, 5 months)")
                    markdownText("*Reason:*\nAll vowel keys aren't working.")
                    markdownText("*Specs:*\n\"Cheetah Pro 15\" - Fast, really fast\"")
                }
            }
            actions {
                elements {
                    button {
                        text("Approve", emoji = true)
                        style("primary")
                        value("click_me_123")
                    }
                    button {
                        text("Deny", emoji = true)
                        style("danger")
                        value("click_me_123")
                    }
                }
            }
        }
        val original = """
{
	"blocks": [
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "You have a new request:\n*<fakeLink.toEmployeeProfile.com|Fred Enriquez - New device request>*"
			}
		},
		{
			"type": "section",
			"fields": [
				{
					"type": "mrkdwn",
					"text": "*Type:*\nComputer (laptop)"
				},
				{
					"type": "mrkdwn",
					"text": "*When:*\nSubmitted Aut 10"
				},
				{
					"type": "mrkdwn",
					"text": "*Last Update:*\nMar 10, 2015 (3 years, 5 months)"
				},
				{
					"type": "mrkdwn",
					"text": "*Reason:*\nAll vowel keys aren't working."
				},
				{
					"type": "mrkdwn",
					"text": "*Specs:*\n\"Cheetah Pro 15\" - Fast, really fast\""
				}
			]
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"emoji": true,
						"text": "Approve"
					},
					"style": "primary",
					"value": "click_me_123"
				},
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"emoji": true,
						"text": "Deny"
					},
					"style": "danger",
					"value": "click_me_123"
				}
			]
		}
	]
}
"""
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n" + expected.toString() + "\n" + actual.toString())
    }

    @Test
    fun notification() {
        val blocks = withBlocks {
            section {
                plainText("Looks like you have a scheduling conflict with this event:", emoji = true)
            }
            divider()
            section {
                markdownText("*<fakeLink.toUserProfiles.com|Iris / Zelda 1-1>*\nTuesday, January 21 4:00-4:30pm\nBuilding 2 - Havarti Cheese (3)\n2 guests")
                accessory {
                    image(imageUrl = "https://api.slack.com/img/blocks/bkb_template_images/notifications.png", altText = "calendar thumbnail")
                }
            }
            context {
                elements {
                    image(imageUrl = "https://api.slack.com/img/blocks/bkb_template_images/notificationsWarningIcon.png", altText = "notifications warning icon")
                }
                markdownText("*Conflicts with Team Huddle: 4:15-4:30pm*")
            }
            divider()
            section {
                markdownText("*Propose a new time:*")
            }
            section {
                markdownText("*Today - 4:30-5pm*\nEveryone is available: @iris, @zelda")
                accessory {
                    button {
                        text(text = "Choose", emoji = true)
                        value("click_me_123")
                    }
                }
            }
            section {
                markdownText("*Tomorrow - 4-4:30pm*\nEveryone is available: @iris, @zelda")
                accessory {
                    button {
                        text(text = "Choose", emoji = true)
                        value("click_me_123")
                    }
                }
            }
            section {
                markdownText("*Tomorrow - 6-6:30pm*\nSome people aren't available: @iris, ~@zelda~")
                accessory {
                    button {
                        text(text = "Choose", emoji = true)
                        value("click_me_123")
                    }
                }
            }
            section {
                markdownText("*<fakelink.ToMoreTimes.com|Show more times>*")
            }
        }
        val original = """
{
	"blocks": [
		{
			"type": "section",
			"text": {
				"type": "plain_text",
				"text": "Looks like you have a scheduling conflict with this event:",
				"emoji": true
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*<fakeLink.toUserProfiles.com|Iris / Zelda 1-1>*\nTuesday, January 21 4:00-4:30pm\nBuilding 2 - Havarti Cheese (3)\n2 guests"
			},
			"accessory": {
				"type": "image",
				"image_url": "https://api.slack.com/img/blocks/bkb_template_images/notifications.png",
				"alt_text": "calendar thumbnail"
			}
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "image",
					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/notificationsWarningIcon.png",
					"alt_text": "notifications warning icon"
				},
				{
					"type": "mrkdwn",
					"text": "*Conflicts with Team Huddle: 4:15-4:30pm*"
				}
			]
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Propose a new time:*"
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Today - 4:30-5pm*\nEveryone is available: @iris, @zelda"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Choose",
					"emoji": true
				},
				"value": "click_me_123"
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Tomorrow - 4-4:30pm*\nEveryone is available: @iris, @zelda"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Choose",
					"emoji": true
				},
				"value": "click_me_123"
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Tomorrow - 6-6:30pm*\nSome people aren't available: @iris, ~@zelda~"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Choose",
					"emoji": true
				},
				"value": "click_me_123"
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*<fakelink.ToMoreTimes.com|Show more times>*"
			}
		}
	]
}
"""
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n" + expected.toString() + "\n" + actual.toString())
    }

    @Test
    fun onboarding() {
        val blocks = withBlocks {
            section {
                markdownText("Hey there \uD83D\uDC4B I'm TaskBot. I'm here to help you create and manage tasks in Slack.\nThere are two ways to quickly create tasks:")
            }
            section {
                markdownText("*1️⃣ Use the `/task` command*. Type `/task` followed by a short description of your tasks and I'll ask for a due date (if applicable). Try it out by using the `/task` command in this channel.")
            }
            section {
                markdownText("*2️⃣ Use the _Create a Task_ action.* If you want to create a task from a message, select `Create a Task` in a message's context menu. Try it out by selecting the _Create a Task_ action for this message (shown below).")
            }
            image {
                title("image1", true)
                imageUrl("https://api.slack.com/img/blocks/bkb_template_images/onboardingComplex.jpg")
                altText("image1")
            }
            section {
                markdownText("➕ To start tracking your team's tasks, *add me to a channel* and I'll introduce myself. I'm usually added to a team or project channel. Type `/invite @TaskBot` from the channel or pick a channel on the right.")
                accessory {
                    conversationsSelect {
                        placeholder("Select a channel...", true)
                    }
                }
            }
            divider()
            context {
                markdownText("\uD83D\uDC40 View all tasks with `/task list`\n❓Get help at any time with `/task help` or type *help* in a DM with me")
            }
        }
        val original = """
{
	"blocks": [
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "Hey there 👋 I'm TaskBot. I'm here to help you create and manage tasks in Slack.\nThere are two ways to quickly create tasks:"
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*1️⃣ Use the `/task` command*. Type `/task` followed by a short description of your tasks and I'll ask for a due date (if applicable). Try it out by using the `/task` command in this channel."
			}
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*2️⃣ Use the _Create a Task_ action.* If you want to create a task from a message, select `Create a Task` in a message's context menu. Try it out by selecting the _Create a Task_ action for this message (shown below)."
			}
		},
		{
			"type": "image",
			"title": {
				"type": "plain_text",
				"text": "image1",
				"emoji": true
			},
			"image_url": "https://api.slack.com/img/blocks/bkb_template_images/onboardingComplex.jpg",
			"alt_text": "image1"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "➕ To start tracking your team's tasks, *add me to a channel* and I'll introduce myself. I'm usually added to a team or project channel. Type `/invite @TaskBot` from the channel or pick a channel on the right."
			},
			"accessory": {
				"type": "conversations_select",
				"placeholder": {
					"type": "plain_text",
					"text": "Select a channel...",
					"emoji": true
				}
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "mrkdwn",
					"text": "👀 View all tasks with `/task list`\n❓Get help at any time with `/task help` or type *help* in a DM with me"
				}
			]
		}
	]
}
"""
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n" + expected.toString() + "\n" + actual.toString())
    }
}
