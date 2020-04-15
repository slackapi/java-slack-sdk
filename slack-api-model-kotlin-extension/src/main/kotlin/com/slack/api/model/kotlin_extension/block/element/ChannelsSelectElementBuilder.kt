package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ChannelsSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class ChannelsSelectElementBuilder : Builder<ChannelsSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialChannel: String? = null
    private var responseUrlEnabled: Boolean? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialChannel(channel: String) {
        initialChannel = channel
    }

    fun responseUrlEnabled(enabled: Boolean) {
        responseUrlEnabled = enabled
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): ChannelsSelectElement {
        return ChannelsSelectElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialChannel(initialChannel)
                .confirm(confirm)
                .responseUrlEnabled(responseUrlEnabled)
                .build()
    }
}