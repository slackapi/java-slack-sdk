package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.PlainTextInputElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder

@BlockLayoutBuilder
class PlainTextInputElementBuilder : Builder<PlainTextInputElement> {
    private var actionId: String? = null
    private var placeholder: PlainTextObject? = null
    private var initialValue: String? = null
    private var multiline: Boolean = false
    private var minLength: Int? = null
    private var maxLength: Int? = null

    fun actionId(id: String) {
        actionId = id
    }

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun initialValue(value: String) {
        initialValue = value
    }

    fun multiline(isMultiline: Boolean) {
        multiline = isMultiline
    }

    fun minLength(length: Int) {
        minLength = length
    }

    fun maxLength(length: Int) {
        maxLength = length
    }

    override fun build(): PlainTextInputElement {
        return PlainTextInputElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialValue(initialValue)
                .multiline(multiline)
                .minLength(minLength)
                .maxLength(maxLength)
                .build()
    }
}