package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.SlackConfig
import com.slack.api.model.block.element.RichTextSectionElement.LimitedTextStyle
import com.slack.api.model.block.element.RichTextSectionElement.TextStyle
import com.slack.api.model.kotlin_extension.block.element.BroadcastRange
import com.slack.api.model.kotlin_extension.block.element.ListStyle
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class RichTextBlockTest {

    val gson = GsonFactory.createSnakeCase(SlackConfig().apply { isPrettyResponseLoggingEnabled = true }) // FIXME

    @Test
    fun kitchenSink() {
        val blocks = withBlocks {
            richText {
                richTextSection {
                    text("Check out these different block types with paragraph breaks between them:\n\n")
                }
                richTextPreformatted {
                    text("Hello there, I am preformatted block!\n\nI can have multiple paragraph breaks within the block.")
                }
                richTextSection {
                    text("\nI am rich text with a paragraph break following preformatted text. \n\nI can have multiple paragraph breaks within the block.\n\n")
                }
                richTextQuote {
                    text("I am a basic rich text quote, \n\nI can have multiple paragraph breaks within the block.")
                }
                richTextSection {
                    text("\nI am rich text with a paragraph after the quote block\n\n")
                }
                richTextQuote {
                    text("I am a basic quote block following rich text")
                }
                richTextSection {
                    text("\n")
                }
                richTextPreformatted {
                    text("I am more preformatted text following a quote block")
                }
                richTextSection {
                    text("\n")
                }
                richTextQuote {
                    text("I am a basic quote block following preformatted text")
                }
                richTextSection {
                    text("\n")
                }
                richTextList {
                    style(ListStyle.BULLET)
                    richTextSection {
                        text("list item one")
                    }
                    richTextSection {
                        text("list item two")
                    }
                }
                richTextSection {
                    text("\nI am rich text with a paragraph break after a list")
                }
            }
        }
        val original = """
{
	"blocks": [
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "Check out these different block types with paragraph breaks between them:\n\n"
						}
					]
				},
				{
					"type": "rich_text_preformatted",
					"elements": [
						{
							"type": "text",
							"text": "Hello there, I am preformatted block!\n\nI can have multiple paragraph breaks within the block."
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\nI am rich text with a paragraph break following preformatted text. \n\nI can have multiple paragraph breaks within the block.\n\n"
						}
					]
				},
				{
					"type": "rich_text_quote",
					"elements": [
						{
							"type": "text",
							"text": "I am a basic rich text quote, \n\nI can have multiple paragraph breaks within the block."
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\nI am rich text with a paragraph after the quote block\n\n"
						}
					]
				},
				{
					"type": "rich_text_quote",
					"elements": [
						{
							"type": "text",
							"text": "I am a basic quote block following rich text"
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\n"
						}
					]
				},
				{
					"type": "rich_text_preformatted",
					"elements": [
						{
							"type": "text",
							"text": "I am more preformatted text following a quote block"
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\n"
						}
					]
				},
				{
					"type": "rich_text_quote",
					"elements": [
						{
							"type": "text",
							"text": "I am a basic quote block following preformatted text"
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\n"
						}
					]
				},
				{
					"type": "rich_text_list",
					"style": "bullet",
					"elements": [
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "text",
									"text": "list item one"
								}
							]
						},
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "text",
									"text": "list item two"
								}
							]
						}
					]
				},
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "text",
							"text": "\nI am rich text with a paragraph break after a list"
						}
					]
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
    fun richTextObjects() {
        val blocks = withBlocks {
            richText {
                richTextList {
                    style(ListStyle.BULLET)
                    indent(0)
                    border(0)
                    richTextSection {
                        broadcast(BroadcastRange.HERE)
                    }
                    richTextSection {
                        broadcast(BroadcastRange.CHANNEL)
                    }
                    richTextSection {
                        broadcast(BroadcastRange.EVERYONE)
                    }
                }
            }

            richText {
                richTextList {
                    style(ListStyle.BULLET)
                    indent(1)
                    border(0)
                    richTextSection {
                        color("#C0FFEE")
                    }
                }
            }

            richText {
                richTextList {
                    style(ListStyle.ORDERED)
                    indent(0)
                    border(1)
                    richTextSection {
                        channel("C0123456789")
                    }
                    richTextSection {
                        channel("C0123456789",
                            LimitedTextStyle.builder().bold(true).italic(true).clientHighlight(true).build())
                    }
                }
            }

            richText {
                richTextSection {
                    date(1720710212, "{date_num} at {time}", null, "timey")
                }
            }

            richText {
                richTextSection {
                    emoji("basketball")
                    text(" ")
                    emoji("snowboarder")
                    text(" ")
                    emoji("checkered_flag")
                }
            }

            richText {
                richTextQuote {
                    border(1)
                    link("https://api.slack.com")
                    text(" ")
                    link("https://api.slack.com", "Slack API")
                    text(" ")
                    link("https://api.slack.com", unsafe = true)
                    text(" ")
                    link("https://api.slack.com", "Slack API",
                        style = TextStyle.builder().strike(true).code(true).clientHighlight(true).build())
                }
            }

            richText {
                richTextSection {
                    user("U12345678")
                }
            }

            richText {
                richTextSection {
                    usergroup("S0123456789")
                }
            }
        }
        val original = """
{
	"blocks": [
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_list",
					"elements": [
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "broadcast",
									"range": "here"
								}
							]
						},
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "broadcast",
									"range": "channel"
								}
							]
						},
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "broadcast",
									"range": "everyone"
								}
							]
						}
					],
					"style": "bullet",
					"indent": 0,
					"border": 0
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_list",
					"elements": [
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "color",
									"value": "#C0FFEE"
								}
							]
						}
					],
					"style": "bullet",
					"indent": 1,
					"border": 0
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_list",
					"elements": [
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "channel",
									"channel_id": "C0123456789"
								}
							]
						},
						{
							"type": "rich_text_section",
							"elements": [
								{
									"type": "channel",
									"channel_id": "C0123456789",
									"style": {
										"bold": true,
										"italic": true,
										"strike": false,
										"highlight": false,
										"client_highlight": true,
										"unlink": false
									}
								}
							]
						}
					],
					"style": "ordered",
					"indent": 0,
					"border": 1
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "date",
							"timestamp": 1720710212,
							"format": "{date_num} at {time}",
							"fallback": "timey"
						}
					]
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "emoji",
							"name": "basketball"
						},
						{
							"type": "text",
							"text": " "
						},
						{
							"type": "emoji",
							"name": "snowboarder"
						},
						{
							"type": "text",
							"text": " "
						},
						{
							"type": "emoji",
							"name": "checkered_flag"
						}
					]
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_quote",
					"elements": [
						{
							"type": "link",
							"url": "https://api.slack.com"
						},
						{
							"type": "text",
							"text": " "
						},
						{
							"type": "link",
							"url": "https://api.slack.com",
							"text": "Slack API"
						},
						{
							"type": "text",
							"text": " "
						},
						{
							"type": "link",
							"url": "https://api.slack.com",
							"unsafe": true
						},
						{
							"type": "text",
							"text": " "
						},
						{
							"type": "link",
							"url": "https://api.slack.com",
							"text": "Slack API",
							"style": {
								"bold": false,
								"italic": false,
								"strike": true,
								"highlight": false,
								"client_highlight": true,
								"unlink": false,
								"code": true
							}
						}
					],
					"border": 1
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "user",
							"user_id": "U12345678"
						}
					]
				}
			]
		},
		{
			"type": "rich_text",
			"elements": [
				{
					"type": "rich_text_section",
					"elements": [
						{
							"type": "usergroup",
							"usergroup_id": "S0123456789"
						}
					]
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
