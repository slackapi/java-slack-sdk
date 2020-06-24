package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl

/**
 * Supports a TextObjectContainer holding one to many text objects
 */
class MultiTextObjectContainer : TextObjectDsl {
    val underlying = mutableListOf<TextObject>()

    override fun plainText(text: String, emoji: Boolean?) {
        underlying += PlainTextObject(text, emoji)
    }

    override fun markdownText(text: String, verbatim: Boolean?) {
        underlying += MarkdownTextObject(text, verbatim)
    }
}