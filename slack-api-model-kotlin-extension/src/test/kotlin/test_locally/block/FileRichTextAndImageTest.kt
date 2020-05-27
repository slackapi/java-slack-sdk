package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.FileSource
import com.slack.api.model.kotlin_extension.block.element.RichTextListStyle
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class FileRichTextAndImageTest {
    @Test()
    fun `Verify functionality of rich text builder, image builder, and file builder`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            richText {
                richTextSection {
                    text {
                        text("Apartment Recommendations for ")
                        style(bold = true)
                    }
                    team {
                        teamId("coolteam")
                        style(bold = true, italic = true)
                    }
                    text {
                        text(" for ")
                        style(bold = true)
                    }
                    date("5/30/2020")
                    text(":")
                }
                richTextSection {
                    text {
                        text("According to the following criteria:")
                        style(italic = true)
                    }
                    richTextList {
                        style(RichTextListStyle.BULLETED)
                        indent(2)
                        elements {
                            text("Cost under $2100/month")
                            text("Within 10 miles of San Francisco, CA")
                            text("Included washer/dryer")
                        }
                    }
                    link {
                        text("Click here to change your settings")
                        url("https://some-fake-site.com")
                    }
                }
            }
            divider()
            image {
                blockId("option1img")
                title("Option :one:", emoji = true)
                altText("Outside shot of first option")
                imageUrl("https://some-fake-site.com/option1-fake.jpg")
                fallback("Picture of option 1")
                imageWidth(400)
                imageHeight(300)
                imageBytes(360000)
            }
            richText {
                richTextQuote {
                    text {
                        text("Option 1: ")
                        style(bold = true)
                    }
                    text("The Flats at 150 Fake St.\nCurrent Price: ")
                    text {
                        text("$2050/mo")
                        style(strike = true)
                    }
                    text(" $1800/mo (LIMITED TIME OFFER!)\n\nRecommended for: ")
                    user {
                        userId("UserA")
                        style(italic = true)
                    }
                    text(", ")
                    user("UserB")
                    text(", ")
                    user("UserC")
                    text(", and ")
                    usergroup("outofstate")
                }
            }
            file(externalId = "floorplan-f4k3-2bed1bathA", blockId = "option1floorplan", source = FileSource.REMOTE)
            file(externalId = "floorplan-f4k3r-2bed1bathB", blockId = "option1floorplanalternate", source = "remote")
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
                        "text": "Apartment Recommendations for ",
                        "style": {
                          "bold": true,
                          "italic": false,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "team",
                        "team_id": "coolteam",
                        "style": {
                          "bold": true,
                          "italic": true,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "text",
                        "text": " for ",
                        "style": {
                          "bold": true,
                          "italic": false,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "date",
                        "timestamp": "5/30/2020"
                      },
                      {
                        "type": "text",
                        "text": ":"
                      }
                    ]
                  },
                  {
                    "type": "rich_text_section",
                    "elements": [
                      {
                        "type": "text",
                        "text": "According to the following criteria:",
                        "style": {
                          "bold": false,
                          "italic": true,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "rich_text_list",
                        "elements": [
                          {
                            "type": "text",
                            "text": "Cost under ${'$'}2100/month"
                          },
                          {
                            "type": "text",
                            "text": "Within 10 miles of San Francisco, CA"
                          },
                          {
                            "type": "text",
                            "text": "Included washer/dryer"
                          }
                        ],
                        "style": "bullet",
                        "indent": 2
                      },
                      {
                        "type": "link",
                        "url": "https://some-fake-site.com",
                        "text": "Click here to change your settings"
                      }
                    ]
                  }
                ]
              },
              {
                "type": "divider"
              },
              {
                "type": "image",
                "fallback": "Picture of option 1",
                "image_url": "https://some-fake-site.com/option1-fake.jpg",
                "image_width": 400,
                "image_height": 300,
                "image_bytes": 360000,
                "alt_text": "Outside shot of first option",
                "title": {
                  "type": "plain_text",
                  "text": "Option :one:",
                  "emoji": true
                },
                "block_id": "option1img"
              },
              {
                "type": "rich_text",
                "elements": [
                  {
                    "type": "rich_text_quote",
                    "elements": [
                      {
                        "type": "text",
                        "text": "Option 1: ",
                        "style": {
                          "bold": true,
                          "italic": false,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "text",
                        "text": "The Flats at 150 Fake St.\nCurrent Price: "
                      },
                      {
                        "type": "text",
                        "text": "${'$'}2050/mo",
                        "style": {
                          "bold": false,
                          "italic": false,
                          "strike": true,
                          "code": false
                        }
                      },
                      {
                        "type": "text",
                        "text": " ${'$'}1800/mo (LIMITED TIME OFFER!)\n\nRecommended for: "
                      },
                      {
                        "type": "user",
                        "user_id": "UserA",
                        "style": {
                          "bold": false,
                          "italic": true,
                          "strike": false,
                          "code": false
                        }
                      },
                      {
                        "type": "text",
                        "text": ", "
                      },
                      {
                        "type": "user",
                        "user_id": "UserB"
                      },
                      {
                        "type": "text",
                        "text": ", "
                      },
                      {
                        "type": "user",
                        "user_id": "UserC"
                      },
                      {
                        "type": "text",
                        "text": ", and "
                      },
                      {
                        "type": "usergroup",
                        "usergroup_id": "outofstate"
                      }
                    ]
                  }
                ]
              },
              {
                "type": "file",
                "block_id": "option1floorplan",
                "external_id": "floorplan-f4k3-2bed1bathA",
                "source": "remote"
              },
              {
                "type": "file",
                "block_id": "option1floorplanalternate",
                "external_id": "floorplan-f4k3r-2bed1bathB",
                "source": "remote"
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
    fun `Remaining rich text builder tests`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            richText {
                richTextPreformatted {
                    elements {
                        text("Preformatted text")
                    }
                }
                richTextSection {
                    channel("general")
                    emoji("tada")
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
                      "type": "rich_text_preformatted",
                      "elements": [
                        {
                          "type": "text",
                          "text": "Preformatted text"
                        }
                      ]
                    },
                    {
                      "type": "rich_text_section",
                      "elements": [
                        {
                          "type": "channel",
                          "channel_id": "general"
                        },
                        {
                          "type": "emoji",
                          "name": "tada"
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