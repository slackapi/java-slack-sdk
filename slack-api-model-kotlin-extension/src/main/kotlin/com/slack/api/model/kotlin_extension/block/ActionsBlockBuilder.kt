package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ActionsBlock
import com.slack.api.model.kotlin_extension.block.element.container.MultiBlockElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class ActionsBlockBuilder private constructor(
        private val elementsContainer: MultiBlockElementContainer
) : Builder<ActionsBlock>, BlockElementDsl by elementsContainer {
    private var blockId: String? = null

    constructor() : this(MultiBlockElementContainer())

    fun blockId(id: String) {
        blockId = id
    }

    override fun build(): ActionsBlock {
        return ActionsBlock.builder()
                .blockId(blockId)
                .elements(elementsContainer.underlying)
                .build()
    }
}