package test_locally.block

import com.slack.api.Slack
import com.slack.api.SlackConfig
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.block.element.BlockElements.asElements
import com.slack.api.model.kotlin_extension.block.element.ButtonStyle
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals
import com.slack.api.model.block.element.BlockElements.button as javaButton

class BasicUsageTest {

    @Test
    fun `Can construct a chat message request equivalent to the Java builder`() {
        val actualReq = ChatPostMessageRequest.builder()
                .channel("general")
                .text("User did a thing!")
                .blocks(withBlocks {
                    section {
                        plainText("This is the text in this section")
                    }
                    divider()
                    actions {
                        button {
                            actionId("action-id-value")
                            url("https://www.google.com")
                            style(ButtonStyle.PRIMARY)
                            text("Go to Google")
                            confirm {
                                title("Confirm Navigation")
                                markdownText("Are you *absolutely sure* you want to go to google?")
                                confirm("Yes, let's go!")
                                deny("I'm not sure...")
                            }
                        }
                        button {
                            actionId("action-id-value")
                            url("https://www.google.com")
                            style(ButtonStyle.PRIMARY)
                            text("Go to Google")
                            confirm {
                                title("Confirm Navigation")
                                markdownText("Are you *absolutely sure* you want to go to google?")
                                confirm("Yes, let's go!")
                                deny("I'm not sure...")
                            }
                        }
                    }
                }).build()

        val expectedReq = ChatPostMessageRequest.builder()
                .channel("general")
                .text("User did a thing!")
                .blocks(asBlocks(
                        section { thisSection ->
                            thisSection.text(plainText("This is the text in this section"))
                        },
                        divider(),
                        actions { actionsBlock ->
                            actionsBlock.elements(asElements(
                                    javaButton { thisButton ->
                                        thisButton.actionId("action-id-value")
                                                .text(plainText("Go to Google"))
                                                .url("https://www.google.com")
                                                .style("primary")
                                                .confirm(confirmationDialog { thisConfirmationDialog ->
                                                    thisConfirmationDialog.title(plainText("Confirm Navigation"))
                                                            .text(markdownText("Are you *absolutely sure* you want to go to google?"))
                                                            .confirm(plainText("Yes, let's go!"))
                                                            .deny(plainText("I'm not sure..."))
                                                })
                                    },
                                    javaButton { thisButton ->
                                        thisButton.actionId("action-id-value")
                                                .text(plainText("Go to Google"))
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
        val config = SlackConfig()
        config.isPrettyResponseLoggingEnabled = true
        val gson = GsonFactory.createSnakeCase(config)
        assertEquals(gson.toJson(expectedReq), gson.toJson(actualReq))
    }

    fun sample() {
        val response = Slack.getInstance().methods("xoxb-***").chatPostMessage {
            it.channel("C123")
                    .blocks(withBlocks {
                        section {
                            plainText("This is the text in this section")
                        }
                        divider()
                        actions {
                            button {
                                text("Go to Google")
                                confirm {
                                    title("Confirm Navigation")
                                    markdownText("Are you *absolutely sure* you want to go to google?")
                                    confirm("Yes, let's go!")
                                    deny("I'm not sure...")
                                }
                            }
                        }
                    })
        }
    }
}
