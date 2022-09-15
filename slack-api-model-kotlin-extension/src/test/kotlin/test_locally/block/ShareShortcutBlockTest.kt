package test_locally.block

import com.slack.api.model.block.Blocks.shareShortcut
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class ShareShortcutBlockTest {

    @Test
    fun constructBlocks() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            shareShortcut {
                blockId("b-1")
                title("title")
                appCollaborators(listOf("U1", "U2"))
                url("https://www.example.com")
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"share_shortcut","block_id":"b-1","app_collaborators":["U1","U2"],"title":"title","url":"https://www.example.com"}]"""
        assertEquals(expected, result)
    }
}
