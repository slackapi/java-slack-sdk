package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

// https://api.slack.com/tools/block-kit-builder?mode=modal&template=1
class ModalTemplateTest {

    val gson = GsonFactory.createSnakeCase()

    @Test
    fun poll() {
        // https://api.slack.com/tools/block-kit-builder?mode=modal&view=%7B%22type%22%3A%22modal%22%2C%22title%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Workplace%20check-in%22%2C%22emoji%22%3Atrue%7D%2C%22submit%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Submit%22%2C%22emoji%22%3Atrue%7D%2C%22close%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Cancel%22%2C%22emoji%22%3Atrue%7D%2C%22blocks%22%3A%5B%7B%22type%22%3A%22section%22%2C%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Awave%3A%20Hey%20David!%5Cn%5CnWe%27d%20love%20to%20hear%20from%20you%20how%20we%20can%20make%20this%20place%20the%20best%20place%20you%E2%80%99ve%20ever%20worked.%22%2C%22emoji%22%3Atrue%7D%7D%2C%7B%22type%22%3A%22divider%22%7D%2C%7B%22type%22%3A%22input%22%2C%22label%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22You%20enjoy%20working%20here%20at%20Pistachio%20%26%20Co%22%2C%22emoji%22%3Atrue%7D%2C%22element%22%3A%7B%22type%22%3A%22radio_buttons%22%2C%22options%22%3A%5B%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Strongly%20agree%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%221%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Agree%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%222%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Neither%20agree%20nor%20disagree%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%223%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Disagree%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%224%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Strongly%20disagree%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%225%22%7D%5D%7D%7D%2C%7B%22type%22%3A%22input%22%2C%22label%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22What%20do%20you%20want%20for%20our%20team%20weekly%20lunch%3F%22%2C%22emoji%22%3Atrue%7D%2C%22element%22%3A%7B%22type%22%3A%22multi_static_select%22%2C%22placeholder%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Select%20your%20favorites%22%2C%22emoji%22%3Atrue%7D%2C%22options%22%3A%5B%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Apizza%3A%20Pizza%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-0%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Afried_shrimp%3A%20Thai%20food%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-1%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Adesert_island%3A%20Hawaiian%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-2%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Ameat_on_bone%3A%20Texas%20BBQ%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-3%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Ahamburger%3A%20Burger%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-4%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Ataco%3A%20Tacos%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-5%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Agreen_salad%3A%20Salad%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-6%22%7D%2C%7B%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22%3Astew%3A%20Indian%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22value-7%22%7D%5D%7D%7D%2C%7B%22type%22%3A%22input%22%2C%22label%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22What%20can%20we%20do%20to%20improve%20your%20experience%20working%20here%3F%22%2C%22emoji%22%3Atrue%7D%2C%22element%22%3A%7B%22type%22%3A%22plain_text_input%22%2C%22multiline%22%3Atrue%7D%7D%2C%7B%22type%22%3A%22input%22%2C%22label%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Anything%20else%20you%20want%20to%20tell%20us%3F%22%2C%22emoji%22%3Atrue%7D%2C%22element%22%3A%7B%22type%22%3A%22plain_text_input%22%2C%22multiline%22%3Atrue%7D%2C%22optional%22%3Atrue%7D%5D%7D
        val blocks = withBlocks {
            section {
                plainText(
                        text = ":wave: Hey David!\n\nWe'd love to hear from you how we can make this place the best place you’ve ever worked.",
                        emoji = true
                )
            }
            divider()
            input {
                label("You enjoy working here at Pistachio & Co", true)
                element {
                    radioButtons {
                        options {
                            option {
                                plainText("Strongly agree", emoji = true)
                                value("1")
                            }
                            option {
                                plainText("Agree", emoji = true)
                                value("2")
                            }
                            option {
                                plainText("Neither agree nor disagree", emoji = true)
                                value("3")
                            }
                            option {
                                plainText("Disagree", emoji = true)
                                value("4")
                            }
                            option {
                                plainText("Strongly disagree", emoji = true)
                                value("5")
                            }
                        }
                    }
                }
            }
            input {
                label("What do you want for our team weekly lunch?", true)
                element {
                    multiStaticSelect {
                        placeholder("Select your favorites", true)
                        options {
                            option {
                                plainText(":pizza: Pizza", emoji = true)
                                value("value-0")
                            }
                            option {
                                plainText(":fried_shrimp: Thai food", emoji = true)
                                value("value-1")
                            }
                            option {
                                plainText(":desert_island: Hawaiian", emoji = true)
                                value("value-2")
                            }
                            option {
                                plainText(":meat_on_bone: Texas BBQ", emoji = true)
                                value("value-3")
                            }
                            option {
                                plainText(":hamburger: Burger", emoji = true)
                                value("value-4")
                            }
                            option {
                                plainText(":taco: Tacos", emoji = true)
                                value("value-5")
                            }
                            option {
                                plainText(":green_salad: Salad", emoji = true)
                                value("value-6")
                            }
                            option {
                                plainText(":stew: Indian", emoji = true)
                                value("value-7")
                            }
                        }
                    }
                }
            }
            input {
                label("What can we do to improve your experience working here?", true)
                // can skip "element"
                plainTextInput {
                    multiline(true)
                }
            }
            input {
                label("Anything else you want to tell us?", true)
                element {
                    plainTextInput {
                        multiline(true)
                        focusOnLoad(true)
                    }
                }
                optional(true)
            }
        }
        val original = """
{
	"type": "modal",
	"title": {
		"type": "plain_text",
		"text": "Workplace check-in",
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
			"type": "section",
			"text": {
				"type": "plain_text",
				"text": ":wave: Hey David!\n\nWe'd love to hear from you how we can make this place the best place you’ve ever worked.",
				"emoji": true
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "input",
			"label": {
				"type": "plain_text",
				"text": "You enjoy working here at Pistachio & Co",
				"emoji": true
			},
			"element": {
				"type": "radio_buttons",
				"options": [
					{
						"text": {
							"type": "plain_text",
							"text": "Strongly agree",
							"emoji": true
						},
						"value": "1"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Agree",
							"emoji": true
						},
						"value": "2"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Neither agree nor disagree",
							"emoji": true
						},
						"value": "3"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Disagree",
							"emoji": true
						},
						"value": "4"
					},
					{
						"text": {
							"type": "plain_text",
							"text": "Strongly disagree",
							"emoji": true
						},
						"value": "5"
					}
				]
			},
			"optional": false
		},
		{
			"type": "input",
			"label": {
				"type": "plain_text",
				"text": "What do you want for our team weekly lunch?",
				"emoji": true
			},
			"element": {
				"type": "multi_static_select",
				"placeholder": {
					"type": "plain_text",
					"text": "Select your favorites",
					"emoji": true
				},
				"options": [
					{
						"text": {
							"type": "plain_text",
							"text": ":pizza: Pizza",
							"emoji": true
						},
						"value": "value-0"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":fried_shrimp: Thai food",
							"emoji": true
						},
						"value": "value-1"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":desert_island: Hawaiian",
							"emoji": true
						},
						"value": "value-2"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":meat_on_bone: Texas BBQ",
							"emoji": true
						},
						"value": "value-3"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":hamburger: Burger",
							"emoji": true
						},
						"value": "value-4"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":taco: Tacos",
							"emoji": true
						},
						"value": "value-5"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":green_salad: Salad",
							"emoji": true
						},
						"value": "value-6"
					},
					{
						"text": {
							"type": "plain_text",
							"text": ":stew: Indian",
							"emoji": true
						},
						"value": "value-7"
					}
				]
			},
			"optional": false
		},
		{
			"type": "input",
			"label": {
				"type": "plain_text",
				"text": "What can we do to improve your experience working here?",
				"emoji": true
			},
			"element": {
				"type": "plain_text_input",
				"multiline": true
			},
			"optional": false
		},
		{
			"type": "input",
			"label": {
				"type": "plain_text",
				"text": "Anything else you want to tell us?",
				"emoji": true
			},
			"element": {
				"type": "plain_text_input",
				"multiline": true,
                "focus_on_load":true
			},
			"optional": true
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
    fun listOfInformation() {
        val blocks = withBlocks {
            section {
                markdownText(":tada: You're all set! This is your booking summary.")
            }
            divider()
            section {
                fields {
                    markdownText("*Attendee*\nKatie Chen")
                    markdownText("*Date*\nOct 22-23")
                }
            }
            context {
                elements {
                    markdownText(":house: Accommodation")
                }
            }
            divider()
            section {
                markdownText("*Redwood Suite*\n*Share with 2 other person.* Studio home. Modern bathroom. TV. Heating. Full kitchen. Patio with lounge chairs and campfire style fire pit and grill.")
                accessory {
                    image(imageUrl = "https://api.slack.com/img/blocks/bkb_template_images/redwood-suite.png", altText = "Redwood Suite")
                }
            }
            context {
                elements {
                    markdownText(":fork_and_knife: Food & Dietary restrictions")
                }
            }
            divider()
            section {
                markdownText("*All-rounder*\nYou eat most meats, seafood, dairy and vegetables.")
            }
            context {
                markdownText(":woman-running: Activities")
            }
            divider()
            section {
                markdownText("*Winery tour and tasting*")
                fields {
                    plainText("Wednesday, Oct 22 2019, 2pm-5pm", emoji = true)
                    plainText("Hosted by Sandra Mullens", emoji = true)
                }
            }
            section {
                markdownText("*Sunrise hike to Mount Amazing*")
                fields {
                    plainText("Thursday, Oct 23 2019, 5:30am", emoji = true)
                    plainText("Hosted by Jordan Smith", emoji = true)
                }
            }
            section {
                markdownText("*Design systems brainstorm*")
                fields {
                    plainText("Thursday, Oct 23 2019, 11a", emoji = true)
                    plainText("Hosted by Mary Lee", emoji = true)
                }
            }
        }
        val original = """
{
	"type": "modal",
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
	"title": {
		"type": "plain_text",
		"text": "Your itinerary",
		"emoji": true
	},
	"blocks": [
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": ":tada: You're all set! This is your booking summary."
			}
		},
		{
			"type": "divider"
		},
		{
			"type": "section",
			"fields": [
				{
					"type": "mrkdwn",
					"text": "*Attendee*\nKatie Chen"
				},
				{
					"type": "mrkdwn",
					"text": "*Date*\nOct 22-23"
				}
			]
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "mrkdwn",
					"text": ":house: Accommodation"
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
				"text": "*Redwood Suite*\n*Share with 2 other person.* Studio home. Modern bathroom. TV. Heating. Full kitchen. Patio with lounge chairs and campfire style fire pit and grill."
			},
			"accessory": {
				"type": "image",
				"image_url": "https://api.slack.com/img/blocks/bkb_template_images/redwood-suite.png",
				"alt_text": "Redwood Suite"
			}
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "mrkdwn",
					"text": ":fork_and_knife: Food & Dietary restrictions"
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
				"text": "*All-rounder*\nYou eat most meats, seafood, dairy and vegetables."
			}
		},
		{
			"type": "context",
			"elements": [
				{
					"type": "mrkdwn",
					"text": ":woman-running: Activities"
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
				"text": "*Winery tour and tasting*"
			},
			"fields": [
				{
					"type": "plain_text",
					"text": "Wednesday, Oct 22 2019, 2pm-5pm",
					"emoji": true
				},
				{
					"type": "plain_text",
					"text": "Hosted by Sandra Mullens",
					"emoji": true
				}
			]
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Sunrise hike to Mount Amazing*"
			},
			"fields": [
				{
					"type": "plain_text",
					"text": "Thursday, Oct 23 2019, 5:30am",
					"emoji": true
				},
				{
					"type": "plain_text",
					"text": "Hosted by Jordan Smith",
					"emoji": true
				}
			]
		},
		{
			"type": "section",
			"text": {
				"type": "mrkdwn",
				"text": "*Design systems brainstorm*"
			},
			"fields": [
				{
					"type": "plain_text",
					"text": "Thursday, Oct 23 2019, 11a",
					"emoji": true
				},
				{
					"type": "plain_text",
					"text": "Hosted by Mary Lee",
					"emoji": true
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
