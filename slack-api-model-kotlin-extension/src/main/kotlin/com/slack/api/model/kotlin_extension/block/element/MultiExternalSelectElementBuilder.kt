package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.MultiExternalSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class MultiExternalSelectElementBuilder : Builder<MultiExternalSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialOptions: List<OptionObject>? = null
    private var minQueryLength: Int? = null
    private var maxSelectedItems: Int? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialOptions(builder: OptionObjectDsl.() -> Unit) {
        initialOptions = MultiOptionContainer().apply(builder).underlying
    }

    fun minQueryLength(length: Int) {
        minQueryLength = length
    }

    fun maxSelectedItems(max: Int) {
        maxSelectedItems = max
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): MultiExternalSelectElement {
        return MultiExternalSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialOptions(initialOptions)
                .minQueryLength(minQueryLength)
                .maxSelectedItems(maxSelectedItems)
                .confirm(confirm)
                .build()
    }
}