package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.kotlin_extension.block.composition.container.MultiTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.container.SingleTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl
import com.slack.api.model.kotlin_extension.block.element.container.SingleBlockElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class SectionBlockBuilder private constructor(
        private val textContainer: SingleTextObjectContainer,
        private val accessoryContainer: SingleBlockElementContainer
) : Builder<SectionBlock>, TextObjectDsl by textContainer, BlockElementDsl by accessoryContainer {
    private var blockId: String? = null

    // Need to separate "fields" and "fieldsContainer" because the delegate makes the list non-null by default
    private var fields: List<TextObject>? = null

    constructor() : this(SingleTextObjectContainer(), SingleBlockElementContainer())

    fun blockId(id: String) {
        blockId = id
    }

    fun fields(builder: TextObjectDsl.() -> Unit) {
        fields = MultiTextObjectContainer().apply(builder).underlying
    }

    fun accessory(builder: BlockElementDsl.() -> Unit) {
        accessoryContainer.apply(builder)
    }

    override fun build(): SectionBlock {
        return SectionBlock.builder()
                .blockId(blockId)
                .fields(fields)
                .accessory(accessoryContainer.underlying)
                .text(textContainer.underlying)
                .build()
    }
}
