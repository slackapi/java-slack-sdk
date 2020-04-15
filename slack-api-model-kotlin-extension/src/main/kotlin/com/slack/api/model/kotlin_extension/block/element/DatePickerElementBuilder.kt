package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.DatePickerElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class DatePickerElementBuilder : Builder<DatePickerElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialDate: String? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialDate(date: String) {
        initialDate = date
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): DatePickerElement {
        return DatePickerElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialDate(initialDate)
                .confirm(confirm)
                .build()
    }
}