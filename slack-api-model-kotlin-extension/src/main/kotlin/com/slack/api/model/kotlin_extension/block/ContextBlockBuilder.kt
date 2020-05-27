package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ContextBlock
import com.slack.api.model.kotlin_extension.block.container.MultiContextBlockElementContainer
import com.slack.api.model.kotlin_extension.block.dsl.ContextBlockElementDsl

@BlockLayoutBuilder
class ContextBlockBuilder private constructor(
        private val elementsContainer: MultiContextBlockElementContainer
) : Builder<ContextBlock>, ContextBlockElementDsl by elementsContainer {
    private var blockId: String? = null

    constructor() : this(MultiContextBlockElementContainer())

    fun blockId(id: String) {
        blockId = id
    }

    fun elements(builder: ContextBlockElementDsl.() -> Unit) {
        this.elementsContainer.apply(builder)
    }

    override fun build(): ContextBlock {
        return ContextBlock.builder()
                .blockId(blockId)
                .elements(elementsContainer.underlying)
                .build()
    }
}