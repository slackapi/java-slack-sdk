package com.slack.api.model.block.element

import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.composition.ConfirmationDialogBuilder
import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject

@SlackAPIBuilder
class ChannelsSelectBuilder(
        private val initialChannel: String?,
        private val actionID: String?,
        private val responseURLEnabled: Boolean?
) {
    private var placeholder: PlainTextObject? = null
    private var confirmationDialog: ConfirmationDialogObject? = null

    init {
        if (actionID?.length ?: 0 > 255) throw IllegalArgumentException("Channel select actionID cannot be longer than 255 characters.")
    }

    fun placeholder(text: String, emoji: Boolean? = null) {
        if (text.length > 150) throw IllegalArgumentException("Channel select placeholder text cannot be longer than 150 characters.")
        placeholder = PlainTextObject(text, emoji)
    }
    fun confirmationDialog(buildDialog: ConfirmationDialogBuilder.() -> Unit) {
        confirmationDialog = ConfirmationDialogBuilder().apply(buildDialog).build()
    }

    fun build(): ChannelsSelectElement {
        return ChannelsSelectElement(placeholder, actionID, initialChannel, confirmationDialog, responseURLEnabled)
    }
}