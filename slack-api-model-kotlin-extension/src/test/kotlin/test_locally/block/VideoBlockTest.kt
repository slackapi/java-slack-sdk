package test_locally.block

import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class VideoBlockTest {

    @Test
    fun constructBlocks() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            video {
                blockId("b-1")
                title {
                    plainText("Title")
                }
                description {
                    plainText("Test")
                }
                videoUrl("https://www.example.com")
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"video","title":{"type":"plain_text","text":"Title"},"description":{"type":"plain_text","text":"Test"},"video_url":"https://www.example.com","block_id":"b-1"}]"""
        assertEquals(expected, result)
    }
}
