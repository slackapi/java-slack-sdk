package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.MultiChannelsSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class MultiChannelsSelectElementBuilder : Builder<MultiChannelsSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialChannels: List<String>? = null
    private var maxSelectedItems: Int? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialChannels(vararg channels: String) {
        initialChannels = channels.toList()
    }

    fun maxSelectedItems(maxItems: Int) {
        maxSelectedItems = maxItems
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): MultiChannelsSelectElement {
        return MultiChannelsSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialChannels(initialChannels)
                .confirm(confirm)
                .maxSelectedItems(maxSelectedItems)
                .build()
    }
}