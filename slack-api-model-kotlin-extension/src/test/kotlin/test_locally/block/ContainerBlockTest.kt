package test_locally.block

import com.slack.api.model.block.ContainerBlock
import com.slack.api.model.block.DividerBlock
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ContainerBlockTest {

    @Test
    fun constructBasicContainer() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            container {
                blockId("container-1")
                title("Container Title")
                width("wide")
                isCollapsible(true)
                defaultCollapsed(false)
                hasHeaderDivider(true)
                icon("https://example.com/icon.png", "icon")
                childBlocks {
                    divider()
                }
            }
        }
        assertEquals(1, blocks.size)
        val container = blocks[0] as ContainerBlock
        assertEquals("container", container.type)
        assertEquals("container-1", container.blockId)
        assertEquals("Container Title", container.title.text)
        assertEquals("wide", container.width)
        assertEquals(true, container.isCollapsible)
        assertEquals(false, container.defaultCollapsed)
        assertEquals(true, container.hasHeaderDivider)
        assertNotNull(container.icon)
        assertEquals(1, container.childBlocks.size)
        assertTrue(container.childBlocks[0] is DividerBlock)

        val json = gson.toJson(blocks)
        assertTrue(json.contains("\"type\":\"container\""))
        assertTrue(json.contains("\"block_id\":\"container-1\""))
    }

    @Test
    fun constructContainerWithPlainTextObject() {
        val titleObj = PlainTextObject.builder().text("Title with emoji").emoji(true).build()
        val blocks = withBlocks {
            container {
                title(titleObj)
                childBlocks {
                    divider()
                }
            }
        }
        val container = blocks[0] as ContainerBlock
        assertEquals("Title with emoji", container.title.text)
    }

    @Test
    fun constructContainerWithSubtitle() {
        val blocks = withBlocks {
            container {
                title("Title")
                subtitle("Plain subtitle")
                childBlocks {
                    divider()
                }
            }
        }
        val container = blocks[0] as ContainerBlock
        assertNotNull(container.subtitle)
        assertEquals("Plain subtitle", container.subtitle.text)
    }

    @Test
    fun constructContainerWithMarkdownSubtitle() {
        val blocks = withBlocks {
            container {
                title("Title")
                subtitle(MarkdownTextObject.builder().text("*Bold* subtitle").build())
                childBlocks {
                    divider()
                }
            }
        }
        val container = blocks[0] as ContainerBlock
        assertNotNull(container.subtitle)
        assertEquals("mrkdwn", container.subtitle.type)
    }

    @Test
    fun constructContainerWithMultipleChildBlocks() {
        val blocks = withBlocks {
            container {
                title("Title")
                childBlocks {
                    section {
                        markdownText("Section inside container")
                    }
                    divider()
                }
            }
        }
        val container = blocks[0] as ContainerBlock
        assertEquals(2, container.childBlocks.size)
        assertTrue(container.childBlocks[0] is SectionBlock)
        assertTrue(container.childBlocks[1] is DividerBlock)
    }

    @Test
    fun constructContainerWithoutOptionalFields() {
        val blocks = withBlocks {
            container {
                title("Minimal")
                childBlocks {
                    divider()
                }
            }
        }
        val container = blocks[0] as ContainerBlock
        assertNull(container.blockId)
        assertNull(container.width)
        assertNull(container.isCollapsible)
        assertNull(container.defaultCollapsed)
        assertNull(container.hasHeaderDivider)
        assertNull(container.icon)
        assertNull(container.richTextTitle)
    }

    @Test
    fun roundTripSerialization() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            container {
                blockId("rt-1")
                title("Round trip")
                subtitle("Sub")
                width("wide")
                isCollapsible(true)
                childBlocks {
                    divider()
                }
            }
        }
        val json = gson.toJson(blocks)
        val reparsed = gson.fromJson(json, Array<ContainerBlock>::class.java)
        assertEquals("rt-1", reparsed[0].blockId)
        assertEquals("Round trip", reparsed[0].title.text)
        assertEquals("wide", reparsed[0].width)
    }
}
