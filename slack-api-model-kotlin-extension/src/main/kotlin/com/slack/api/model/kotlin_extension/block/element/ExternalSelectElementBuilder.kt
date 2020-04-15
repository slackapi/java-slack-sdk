package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ExternalSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder

@BlockLayoutBuilder
class ExternalSelectElementBuilder : Builder<ExternalSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialOption: OptionObject? = null
    private var minQueryLength: Int? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    fun minQueryLength(length: Int) {
        minQueryLength = length
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): ExternalSelectElement {
        return ExternalSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialOption(initialOption)
                .minQueryLength(minQueryLength)
                .confirm(confirm)
                .build()
    }
}