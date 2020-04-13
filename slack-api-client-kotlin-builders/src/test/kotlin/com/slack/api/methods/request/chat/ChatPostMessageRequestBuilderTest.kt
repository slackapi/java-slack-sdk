package com.slack.api.methods.request.chat

import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.block.element.BlockElements.asElements
import com.slack.api.model.block.element.BlockElements.button
import com.slack.api.model.block.element.ButtonBuilder
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp
import kotlin.test.assertEquals

// This is just a placeholder so we can invoke the builder as you'd expect
fun buildChatPostMessageRequest(
        token: String? = null,
        username: String? = null,
        threadTs: String? = null,
        channel: String? = null,
        text: String? = null,
        parse: String = "none",
        linkNames: Boolean = false,
        blocksAsString: String? = null,
        unfurlLinks: Boolean = false,
        unfurlMedia: Boolean = false,
        isAsUser: Boolean? = null,
        markdown: Boolean = true,
        iconURL: String? = null,
        iconEmoji: String? = null,
        replyBroadcast: Boolean = false,
        buildMessageRequest: ChatPostMessageRequestBuilder.() -> Unit
): ChatPostMessageRequest {
    return ChatPostMessageRequestBuilder(
            token, username, threadTs, channel, text, parse, linkNames, blocksAsString, unfurlLinks, unfurlMedia, isAsUser, markdown, iconURL, iconEmoji, replyBroadcast
    ).apply(buildMessageRequest).build()
}

class ChatPostMessageRequestBuilderTest {
    @Test
    fun `Can construct a chat message request equivalent to the Java builder`() {
        val chatPostMessageReq = buildChatPostMessageRequest(channel = "general", text = "User did a thing!") {
            blocks {
                section {
                    plainText("This is the text in this section")
                }
                divider()
                actions {
                    button(url = "https://www.google.com", style = ButtonBuilder.ButtonStyle.PRIMARY) {
                        plainText("Go to Google")
                        confirmationDialog {
                            title("Confirm Navigation")
                            markdownText("Are you *absolutely sure* you want to go to google?")
                            confirm("Yes, let's go!")
                            deny("I'm not sure...")
                        }
                    }
                }
            }
        }

        val expectedMessageRequest = ChatPostMessageRequest.ChatPostMessageRequestBuilder()
                .channel("general")
                .text("User did a thing!")
                .parse("none") // The docs say this is "none" by default & I modified the builder to reflect, but the lombok builder makes this null
                .blocks(asBlocks(
                        section { thisSection ->
                            thisSection.text(plainText("This is the text in this section"))
                        },
                        divider(),
                        actions { actionsBlock ->
                            actionsBlock.elements(asElements(
                                    button { thisButton ->
                                        thisButton.text(plainText("Go to Google"))
                                                .url("https://www.google.com")
                                                .style("primary")
                                                .confirm(confirmationDialog { thisConfirmationDialog ->
                                                    thisConfirmationDialog.title(plainText("Confirm Navigation"))
                                                            .text(markdownText("Are you *absolutely sure* you want to go to google?"))
                                                            .confirm(plainText("Yes, let's go!"))
                                                            .deny(plainText("I'm not sure..."))
                                                })
                                    }
                            ))
                        }
                ))
                .build()

        // Doing a toString() comparison because lombok data classes don't perform deep list equality comparisons
        assertEquals(expectedMessageRequest.toString(), chatPostMessageReq.toString())
    }
}