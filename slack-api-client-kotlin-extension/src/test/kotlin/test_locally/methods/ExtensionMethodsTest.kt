package test_locally.methods

import com.slack.api.methods.kotlin_extension.request.chat.blocks
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.model.block.Blocks.asBlocks
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.composition.BlockCompositions.*
import org.junit.Test
import kotlin.test.assertEquals

class ExtensionMethodsTest {

    @Test
    fun `Can add blocks to message post request via extension`() {
        val messageRequest = ChatPostMessageRequest.builder()
                .blocks {
                    section {
                        markdownText("You can get to **google** via <this link|https://www.google.com> :thumbsup: :tada:")
                        fields {
                            plainText("30 clicks")
                            plainText("0.001 second load time")
                        }
                    }
                }
                .build()

        val expected = ChatPostMessageRequest.builder()
                .blocks(asBlocks(
                        section { thisSection ->
                            thisSection
                                    .text(markdownText("You can get to **google** via <this link|https://www.google.com> :thumbsup: :tada:"))
                                    .fields(asSectionFields(
                                            plainText("30 clicks"),
                                            plainText("0.001 second load time")
                                    ))
                        }
                ))
                .build()

        assertEquals(expected.toString(), messageRequest.toString())
    }
}