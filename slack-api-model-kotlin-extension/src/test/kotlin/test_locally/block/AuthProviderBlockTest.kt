package test_locally.block

import com.slack.api.model.block.AuthProviderBlock
import com.slack.api.model.block.Blocks.authProvider
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.model.block.composition.BlockCompositions.option
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElements.overflowMenu
import com.slack.api.model.kotlin_extension.block.withBlocks
import org.junit.Test
import kotlin.test.assertEquals

class AuthProviderBlockTest {

    @Test
    fun constructDisconnectedAuthProvider() {
        val expected = authProvider { it
            .value("acme")
            .name(plainText("Acme"))
            .isConnected(false)
            .authUrl("https://acme.example.com/oauth")
        }

        val actual = withBlocks {
            authProvider {
                value("acme")
                name("Acme")
                isConnected(false)
                authUrl("https://acme.example.com/oauth")
            }
        }

        assertEquals(listOf(expected), actual)
    }

    @Test
    fun constructConnectedAuthProviderWithAllFields() {
        val expected = authProvider { it
            .blockId("auth1")
            .value("acme")
            .name(plainText("Acme", true))
            .isConnected(true)
            .description(plainText("Search Acme docs"))
            .iconUrl("https://acme.example.com/icon.png")
            .authUrl("https://acme.example.com/oauth")
            .disconnectUrl("https://acme.example.com/disconnect")
            .connectedIdentity(plainText("jane@acme.example.com"))
            .connectedContext(plainText("Acme Corp"))
            .accessory(overflowMenu { menu -> menu
                .actionId("acme_menu")
                .options(listOf(
                    option(plainText("Switch accounts"), "switch"),
                    option(plainText("Disconnect"), "disconnect")
                ))
            })
            .fallback(listOf(section { s -> s.text(markdownText("Connected to Acme")) }))
        }

        val actual = withBlocks {
            authProvider {
                blockId("auth1")
                value("acme")
                name("Acme", emoji = true)
                isConnected(true)
                description("Search Acme docs")
                iconUrl("https://acme.example.com/icon.png")
                authUrl("https://acme.example.com/oauth")
                disconnectUrl("https://acme.example.com/disconnect")
                connectedIdentity("jane@acme.example.com")
                connectedContext("Acme Corp")
                accessory {
                    actionId("acme_menu")
                    options {
                        option {
                            plainText("Switch accounts")
                            value("switch")
                        }
                        option {
                            plainText("Disconnect")
                            value("disconnect")
                        }
                    }
                }
                fallback {
                    section {
                        markdownText("Connected to Acme")
                    }
                }
            }
        }

        assertEquals(listOf(expected), actual)
    }

    @Test
    fun constructAuthProviderWithTextObjects() {
        val name = plainText("Acme", true)
        val identity = plainText("jane@acme.example.com")

        val actual = withBlocks {
            authProvider {
                value("acme")
                name(name)
                isConnected(true)
                connectedIdentity(identity)
            }
        }

        val block = actual[0] as AuthProviderBlock
        assertEquals(name, block.name)
        assertEquals(identity, block.connectedIdentity)
    }
}
