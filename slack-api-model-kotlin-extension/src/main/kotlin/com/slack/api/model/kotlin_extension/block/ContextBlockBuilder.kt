package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ContextBlock
import com.slack.api.model.kotlin_extension.block.container.MultiContextBlockElementContainer
import com.slack.api.model.kotlin_extension.block.dsl.ContextBlockElementDsl
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementDsl

@BlockLayoutBuilder
class ContextBlockBuilder private constructor(
    private val elementsContainer: MultiContextBlockElementContainer
) : Builder<ContextBlock>, ContextBlockElementDsl by elementsContainer {
    private var blockId: String? = null

    constructor() : this(MultiContextBlockElementContainer())

    /**
     * A string acting as a unique identifier for a block. You can use this block_id when you receive an
     * interaction payload to identify the source of the action. If not specified, a block_id will be generated.
     * Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#context">Context block documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * An array of image elements and text objects. Maximum number of items is 10.
     *
     * @see BlockElementDsl for the set of supported interactive element objects
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#context">Context block documentation</a>
     */
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