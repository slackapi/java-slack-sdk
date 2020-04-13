package com.slack.api.model.block.element

import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.SingleTextObjectContainerImpl
import com.slack.api.model.block.composition.TextObjectContainer

@SlackAPIBuilder
class OptionBuilder(
        private val value: String?,
        private val url: String?
) : TextObjectContainer {
    private val textObjectDelegate = SingleTextObjectContainerImpl()
    private var description: PlainTextObject? = null

    init {
        if (url?.length ?: 0 > 3000) throw IllegalArgumentException("Option URL cannot be longer than 3000 characters.")
    }

    fun description(text: String, emoji: Boolean? = null) {
        if (text.length > 75) throw IllegalArgumentException("Option description cannot be longer than 75 characters.")
        description = PlainTextObject(text, emoji)
    }
    override fun plainText(text: String, emoji: Boolean?) = textObjectDelegate.plainText(text, emoji)
    override fun markdownText(text: String, verbatim: Boolean?)  = textObjectDelegate.markdownText(text, verbatim)

    fun build(): OptionObject {
        return OptionObject(textObjectDelegate.constructedTextObject, value, description, url)
    }
}