package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.FileSource
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class FileAndImageTest {
    @Test()
    fun `Verify functionality of image builder and file builder`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
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
            divider()
            image {
                blockId("option2img")
                title("Option :two:", emoji = true)
                altText("Outside shot of second option")
                slackFile {
                    url("https://some-fake-site.com/option2-fake.jpg")
                }
                fallback("Picture of option 2")
            }
            divider()
            file(externalId = "floorplan-f4k3-2bed1bathA", blockId = "option1floorplan", source = FileSource.REMOTE)
            file(externalId = "floorplan-f4k3r-2bed1bathB", blockId = "option1floorplanalternate", source = "remote")
        }
        val original = """
            {
            "blocks": [
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
                "type": "divider"
              },
              {
                "type": "image",
                "fallback": "Picture of option 2",
                "slack_file": {
                  "url": "https://some-fake-site.com/option2-fake.jpg"
                },
                "alt_text": "Outside shot of second option",
                "title": {
                  "type": "plain_text",
                  "text": "Option :two:",
                  "emoji": true
                },
                "block_id": "option2img"
              },
              {
                "type": "divider"
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
}