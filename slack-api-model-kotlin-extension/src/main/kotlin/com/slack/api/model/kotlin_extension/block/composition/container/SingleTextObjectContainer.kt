package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl

/**
 * Supports a TextObjectContainer holding exactly one text object
 */
class SingleTextObjectContainer : TextObjectDsl {
    var underlying: TextObject? = null

    override fun plainText(text: String, emoji: Boolean?) {
        underlying = PlainTextObject(text, emoji)
    }

    override fun markdownText(text: String, verbatim: Boolean?) {
        underlying = MarkdownTextObject(text, verbatim)
    }
}
