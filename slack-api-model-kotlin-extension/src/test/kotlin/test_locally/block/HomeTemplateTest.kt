package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.element.ButtonStyle
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

// https://api.slack.com/tools/block-kit-builder?mode=appHome&template=1
class HomeTemplateTest {

    private val gson = GsonFactory.createSnakeCase()

    @Test
    fun projectTracker() {
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
                    button {
                        text(text = "Help", emoji = true)
                        value("help")
                        accessibilityLabel("This label will be read out by screen readers")
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
				},
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Help",
						"emoji": true
					},
					"value": "help",
					"accessibility_label": "This label will be read out by screen readers"
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

    @Test
    fun calendar() {
        val blocks = withBlocks {
            section {
                markdownText("*Today, 22 October, 12:35pm*")
                accessory {
                    button {
                        text("Manage App Settings", emoji = true)
                        value("settings")
                    }
                }
            }
            actions {
                elements {
                    datePicker {
                        initialDate("2019-10-22")
                        placeholder("Select a date", emoji = true)
                    }
                    timePicker {
                        initialTime("12:35")
                        placeholder("Select a time", emoji = true)
                    }
                }
            }
            divider()
            section {
                markdownText("*<fakelink.toUrl.com|Marketing weekly sync>*\n11:30am — 12:30pm  |  SF500 · 7F · Saturn (5)\nStatus: ✅ Going")
                accessory {
                    overflowMenu {
                        options {
                            option {
                                plainText("View Event Details", emoji = true)
                                value("view_event_details")
                            }
                            option {
                                plainText("Change Response", emoji = true)
                                value("change_response")
                            }
                        }
                    }
                }
            }
            actions {
                elements {
                    button {
                        text("Join Video Call", emoji = true)
                        style(ButtonStyle.PRIMARY)
                        value("join")
                    }
                }
            }
            divider()
            section {
                markdownText("*<fakelink.toUrl.com|Design review w/ Platform leads>*\n1:30pm — 2:00pm  |  SF500 · 4F · Finch (4)")
                accessory {
                    overflowMenu {
                        options {
                            option {
                                plainText("View Event Details", emoji = true)
                                value("view_event_details")
                            }
                            option {
                                plainText("Change Response", emoji = true)
                                value("change_response")
                            }
                        }
                    }
                }
            }
            actions {
                elements {
                    staticSelect {
                        placeholder("Going?", emoji = true)
                        options {
                            option {
                                plainText("Going", emoji = true)
                                value("going")
                            }
                            option {
                                plainText("Maybe", emoji = true)
                                value("maybe")
                            }
                            option {
                                plainText("Not going", emoji = true)
                                value("decline")
                            }
                        }
                    }
                }
            }
            divider()
            section {
                markdownText("*<fakelink.toUrl.com|Presentation write-up>*\n4:00pm — 5:30pm  |  SF500 · 7F · Saturn (5)\nStatus: ✅ Going")
                accessory {
                    overflowMenu {
                        options {
                            option {
                                plainText("View Event Details", emoji = true)
                                value("view_event_details")
                            }
                            option {
                                plainText("Change Response", emoji = true)
                                value("change_response")
                            }
                        }
                    }
                }
            }
            actions {
                elements {
                    button {
                        text("Join Video Call", emoji = true)
                        style(ButtonStyle.PRIMARY)
                        value("join")
                    }
                }
            }
            divider()
            context {
                elements {
                    image("https://api.slack.com/img/blocks/bkb_template_images/placeholder.png", altText = "placeholder")
                }
            }
            context {
                elements {
                    markdownText("Past events")
                }
            }
            section {
                markdownText("*Marketing team breakfast*\n8:30am — 9:30am  |  SF500 · 7F · Saturn (5)")
            }
            divider()
            section {
                markdownText("*Coffee chat w/ candidate*\n10:30am — 11:00am  |  SF500 · 10F · Cafe")
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
				"text": "*Today, 22 October, 12:35pm*"
			},
			"accessory": {
				"type": "button",
				"text": {
					"type": "plain_text",
					"text": "Manage App Settings",
					"emoji": true
				},
				"value": "settings"
			}
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "datepicker",
					"initial_date": "2019-10-22",
					"placeholder": {
						"type": "plain_text",
						"text": "Select a date",
						"emoji": true
					}
				},
				{
					"type": "timepicker",
					"initial_time": "12:35",
					"placeholder": {
						"type": "plain_text",
						"text": "Select a time",
						"emoji": true
					}
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
				"text": "*<fakelink.toUrl.com|Marketing weekly sync>*\n11:30am — 12:30pm  |  SF500 · 7F · Saturn (5)\nStatus: ✅ Going"
			},
			"accessory": {
				"type": "overflow",
				"options": [
					{
						"text": {
							"type": "plain_text",
							"text": "View Event Details",
							"emoji": true
						},
						"value": "view_event_details"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Change Response",
							"emoji": true
						},
						"value": "change_response"
					}
				]
			}
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Join Video Call",
						"emoji": true
					},
					"style": "primary",
					"value": "join"
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
				"text": "*<fakelink.toUrl.com|Design review w/ Platform leads>*\n1:30pm — 2:00pm  |  SF500 · 4F · Finch (4)"
			},
			"accessory": {
				"type": "overflow",
				"options": [
					{
						"text": {
							"type": "plain_text",
							"text": "View Event Details",
							"emoji": true
						},
						"value": "view_event_details"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Change Response",
							"emoji": true
						},
						"value": "change_response"
					}
				]
			}
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "static_select",
					"placeholder": {
						"type": "plain_text",
						"text": "Going?",
						"emoji": true
					},
					"options": [
						{
							"text": {
								"type": "plain_text",
								"text": "Going",
								"emoji": true
							},
							"value": "going"
						},
						{
							"text": {
								"type": "plain_text",
								"text": "Maybe",
								"emoji": true
							},
							"value": "maybe"
						},
						{
							"text": {
								"type": "plain_text",
								"text": "Not going",
								"emoji": true
							},
							"value": "decline"
						}
					]
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
				"text": "*<fakelink.toUrl.com|Presentation write-up>*\n4:00pm — 5:30pm  |  SF500 · 7F · Saturn (5)\nStatus: ✅ Going"
			},
			"accessory": {
				"type": "overflow",
				"options": [
					{
						"text": {
							"type": "plain_text",
							"text": "View Event Details",
							"emoji": true
						},
						"value": "view_event_details"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Change Response",
							"emoji": true
						},
						"value": "change_response"
					}
				]
			}
		},
		{
			"type": "actions",
			"elements": [
				{
					"type": "button",
					"text": {
						"type": "plain_text",
						"text": "Join Video Call",
						"emoji": true
					},
					"style": "primary",
					"value": "join"
				}
			]
		},
		{
			"type": "divider"
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
			"type": "context",
			"elements": [
				{
					"type": "mrkdwn",
					"text": "Past events"
				}
			]
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Marketing team breakfast*\n8:30am — 9:30am  |  SF500 · 7F · Saturn (5)"
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Coffee chat w/ candidate*\n10:30am — 11:00am  |  SF500 · 10F · Cafe"
			}
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
