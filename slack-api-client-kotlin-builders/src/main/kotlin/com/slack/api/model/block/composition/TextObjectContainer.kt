package com.slack.api.model.block.composition

import com.slack.api.SlackAPIBuilder

/**
 * Interface for adding one to many "text" elements to this container
 */
@SlackAPIBuilder
interface TextObjectContainer {
    fun plainText(text: String, emoji: Boolean? = null)
    fun markdownText(text: String, verbatim: Boolean? = null)
}

/**
 * Supports a TextObjectContainer holding exactly one text object
 */
class SingleTextObjectContainerImpl : TextObjectContainer {
    var constructedTextObject: TextObject? = null

    override fun plainText(text: String, emoji: Boolean?) {
        constructedTextObject = PlainTextObject(text, emoji)
    }
    override fun markdownText(text: String, verbatim: Boolean?) {
        constructedTextObject = MarkdownTextObject(text, verbatim)
    }
}

/**
 * Supports a TextObjectContainer holding one to many text objects
 */
class MultiTextObjectContainerImpl : TextObjectContainer {
    val addedTextObjects = mutableListOf<TextObject>()

    override fun plainText(text: String, emoji: Boolean?) {
        addedTextObjects += PlainTextObject(text, emoji)
    }
    override fun markdownText(text: String, verbatim: Boolean?) {
        addedTextObjects += MarkdownTextObject(text, verbatim)
    }
}