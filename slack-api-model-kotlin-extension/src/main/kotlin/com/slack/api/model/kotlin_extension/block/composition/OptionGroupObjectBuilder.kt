package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class OptionGroupObjectBuilder private constructor(
        private val optionContainer: MultiOptionContainer
) : Builder<OptionGroupObject>, OptionObjectDsl by optionContainer {
    private var label: PlainTextObject? = null

    constructor() : this(MultiOptionContainer())

    fun label(text: String, emoji: Boolean? = null) {
        label = PlainTextObject(text, emoji)
    }

    override fun build(): OptionGroupObject {
        return OptionGroupObject.builder()
                .label(label)
                .options(optionContainer.underlying)
                .build()
    }
}