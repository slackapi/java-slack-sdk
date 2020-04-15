package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ButtonElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class ButtonElementBuilder : Builder<ButtonElement> {
    private var actionId: String? = null
    private var text: PlainTextObject? = null
    private var url: String? = null
    private var value: String? = null
    private var style: ButtonStyle? = null
    private var confirm: ConfirmationDialogObject? = null

    fun actionId(id: String) {
        actionId = id
    }

    fun plainText(buttonText: String, emoji: Boolean? = null) {
        text = PlainTextObject(buttonText, emoji)
    }

    fun url(text: String) {
        url = text
    }

    fun value(text: String) {
        value = text
    }

    fun style(style: ButtonStyle) {
        this.style = style
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): ButtonElement {
        return ButtonElement.builder()
                .actionId(actionId)
                .url(url)
                .value(value)
                .text(text)
                .style(style?.value)
                .confirm(confirm)
                .build()
    }
}