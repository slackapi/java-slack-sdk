package com.slack.api.model.block.element

import org.junit.Test
import kotlin.test.assertFailsWith

class ButtonBuilderTest {
    @Test
    fun `Can enforce limits and restrictions`() {
        assertFailsWith<IllegalArgumentException> {
            ButtonBuilder(actionID = randomString(256), url = null, value = null, style = ButtonBuilder.ButtonStyle.NO_STYLINGS)
        }

        assertFailsWith<IllegalArgumentException> {
            ButtonBuilder(url = randomString(3001), actionID = null, value = null, style = ButtonBuilder.ButtonStyle.NO_STYLINGS)
        }

        assertFailsWith<java.lang.IllegalArgumentException> {
            ButtonBuilder(value = randomString(2001), url = null, actionID = null, style = ButtonBuilder.ButtonStyle.NO_STYLINGS)
        }

        assertFailsWith<java.lang.IllegalArgumentException> {
            ButtonBuilder(null, null, null, ButtonBuilder.ButtonStyle.NO_STYLINGS).apply {
                plainText(randomString(76))
            }
        }
    }

    private fun randomString(length: Int): String {
        return buildString {
            for (character in 1..length) {
                append("abcdefghijklmnopqrstuvwxyz".random())
            }
        }
    }
}