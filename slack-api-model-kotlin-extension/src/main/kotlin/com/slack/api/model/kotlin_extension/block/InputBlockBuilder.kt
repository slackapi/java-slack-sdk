package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.InputBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.element.container.SingleBlockElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementInputDsl

@BlockLayoutBuilder
class InputBlockBuilder private constructor(
        private val elementContainer: SingleBlockElementContainer
) : Builder<InputBlock>, BlockElementInputDsl by elementContainer {
    private var blockId: String? = null
    private var label: PlainTextObject? = null
    private var hint: PlainTextObject? = null
    private var optional: Boolean = false

    constructor() : this(SingleBlockElementContainer())

    fun blockId(id: String) {
        blockId = id
    }

    fun label(text: String, emoji: Boolean? = null) {
        label = PlainTextObject(text, emoji)
    }

    fun hint(text: String, emoji: Boolean? = null) {
        hint = PlainTextObject(text, emoji)
    }

    fun optional(optional: Boolean) {
        this.optional = optional
    }

    override fun build(): InputBlock {
        return InputBlock.builder()
                .blockId(blockId)
                .label(label)
                .element(elementContainer.underlying)
                .hint(hint)
                .optional(optional)
                .build()
    }
}