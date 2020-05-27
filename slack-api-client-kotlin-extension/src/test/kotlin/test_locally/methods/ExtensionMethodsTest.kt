package test_locally.methods

import com.slack.api.methods.kotlin_extension.request.chat.blocks
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.model.block.Blocks.asBlocks
import com.slack.api.model.block.Blocks.richText
import com.slack.api.model.block.element.BlockElements.*
import com.slack.api.model.block.element.RichTextSectionElement
import org.junit.Test
import kotlin.test.assertEquals

class ExtensionMethodsTest {

    @Test
    fun `Can add blocks to message post request via extension`() {
        val messageRequest = ChatPostMessageRequest.builder()
                .blocks {
                    richText {
                        richTextSection {
                            text("You can get to ")
                            text {
                                text("google")
                                style(bold = true)
                            }
                            text(" via ")
                            link {
                                url("https://www.google.com")
                                text("this link")
                            }
                            text(" ")
                            emoji("thumbsup")
                            text(" ")
                            emoji("tada")
                        }
                    }
                }
                .build()

        val expected = ChatPostMessageRequest.builder()
                .blocks(asBlocks(
                        richText { thisRichText ->
                            thisRichText.elements(asElements(
                                    richTextSection { thisSection ->
                                        thisSection.elements(asRichTextElements(
                                                RichTextSectionElement.Text.builder().text("You can get to ").build(),
                                                RichTextSectionElement.Text.builder().text("google").style(
                                                        RichTextSectionElement.TextStyle.builder().bold(true).build()
                                                ).build(),
                                                RichTextSectionElement.Text.builder().text(" via ").build(),
                                                RichTextSectionElement.Link.builder().text("this link").url("https://www.google.com").build(),
                                                RichTextSectionElement.Text.builder().text(" ").build(),
                                                RichTextSectionElement.Emoji.builder().name("thumbsup").build(),
                                                RichTextSectionElement.Text.builder().text(" ").build(),
                                                RichTextSectionElement.Emoji.builder().name("tada").build()
                                        ))
                                    }
                            ))
                        }
                ))
                .build()

        assertEquals(expected.toString(), messageRequest.toString())
    }
}