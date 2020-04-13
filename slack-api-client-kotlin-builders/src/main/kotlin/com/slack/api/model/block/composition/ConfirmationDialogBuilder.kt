package com.slack.api.model.block.composition

import com.slack.api.SlackAPIBuilder

/**
 * Builder for a Confirmation Dialog
 */
@SlackAPIBuilder
class ConfirmationDialogBuilder : TextObjectContainer {
    private val objectContainerImpl = SingleTextObjectContainerImpl()
    private var title: PlainTextObject? = null
    private var confirm: PlainTextObject? = null
    private var deny: PlainTextObject? = null

    fun title(text: String, emoji: Boolean? = null) {
        title = PlainTextObject(text, emoji)
    }
    override fun plainText(text: String, emoji: Boolean?) = objectContainerImpl.plainText(text, emoji)
    override fun markdownText(text: String, verbatim: Boolean?) = objectContainerImpl.markdownText(text, verbatim)
    fun confirm(text: String, emoji: Boolean? = null) {
        confirm = PlainTextObject(text, emoji)
    }
    fun deny(text: String, emoji: Boolean? = null) {
        deny = PlainTextObject(text, emoji)
    }

    fun build(): ConfirmationDialogObject {
        return ConfirmationDialogObject(title, objectContainerImpl.constructedTextObject, confirm, deny)
    }
}