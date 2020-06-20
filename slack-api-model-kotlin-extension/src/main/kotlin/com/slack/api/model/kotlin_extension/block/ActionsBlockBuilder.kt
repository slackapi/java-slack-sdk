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

    /**
     * A string acting as a unique identifier for a block. You can use this block_id when you receive an
     * interaction payload to identify the source of the action. If not specified, a block_id will be generated.
     * Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#actions">Actions block documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * An array of interactive element objects - buttons, select menus, overflow menus, or date pickers.
     * There is a maximum of 5 elements in each action block.
     *
     * @see BlockElementDsl for the set of supported interactive element objects
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#actions">Actions block documentation</a>
     */
    fun elements(builder: BlockElementDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    override fun build(): ActionsBlock {
        return ActionsBlock.builder()
                .blockId(blockId)
                .elements(elementsContainer.underlying)
                .build()
    }
}