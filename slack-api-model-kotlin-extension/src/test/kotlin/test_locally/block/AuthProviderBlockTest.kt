package test_locally.block

import com.slack.api.model.block.AuthProviderBlock
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthProviderBlockTest {

    @Test
    fun constructConnectedAuthProvider() {
        val blocks = withBlocks {
            authProvider {
                blockId("auth-1")
                value("acme")
                name("Acme")
                isConnected(true)
                description("Search your Acme docs")
                iconUrl("https://acme.example.com/icon.png")
                authUrl("https://acme.example.com/oauth")
                disconnectUrl("https://acme.example.com/disconnect")
                connectedIdentity("jane@acme.example.com")
                connectedContext("Acme Corp")
                accessory {
                    actionId("acme-menu")
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
                        markdownText("Connected to *Acme*")
                    }
                }
            }
        }
        assertEquals(1, blocks.size)
        val block = blocks[0] as AuthProviderBlock
        assertEquals("auth_provider", block.type)
        assertEquals("auth-1", block.blockId)
        assertEquals("acme", block.value)
        assertEquals("Acme", block.name.text)
        assertEquals(true, block.isConnected)
        assertEquals("Search your Acme docs", block.description.text)
        assertEquals("https://acme.example.com/icon.png", block.iconUrl)
        assertEquals("https://acme.example.com/oauth", block.authUrl)
        assertEquals("https://acme.example.com/disconnect", block.disconnectUrl)
        assertEquals("jane@acme.example.com", block.connectedIdentity.text)
        assertEquals("Acme Corp", block.connectedContext.text)
        assertEquals("acme-menu", block.accessory.actionId)
        assertEquals(2, block.accessory.options.size)
        assertEquals(1, block.fallback.size)
        assertTrue(block.fallback[0] is SectionBlock)
    }

    @Test
    fun constructAuthProviderWithoutOptionalFields() {
        val blocks = withBlocks {
            authProvider {
                value("acme")
                name("Acme")
                isConnected(false)
            }
        }
        val block = blocks[0] as AuthProviderBlock
        assertEquals(false, block.isConnected)
        assertNull(block.blockId)
        assertNull(block.description)
        assertNull(block.iconUrl)
        assertNull(block.authUrl)
        assertNull(block.disconnectUrl)
        assertNull(block.connectedIdentity)
        assertNull(block.connectedContext)
        assertNull(block.accessory)
        assertNull(block.fallback)
    }

    @Test
    fun roundTripSerialization() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            authProvider {
                value("acme")
                name("Acme", emoji = true)
                isConnected(false)
                authUrl("https://acme.example.com/oauth")
            }
        }
        val json = gson.toJson(blocks)
        assertTrue(json.contains("\"type\":\"auth_provider\""))
        assertTrue(json.contains("\"is_connected\":false"))
        val reparsed = gson.fromJson(json, Array<AuthProviderBlock>::class.java)
        assertEquals("acme", reparsed[0].value)
        assertEquals("Acme", reparsed[0].name.text)
        assertEquals(true, reparsed[0].name.emoji)
        assertEquals("https://acme.example.com/oauth", reparsed[0].authUrl)
    }
}
