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
        val original = """{
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
}
