package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.RichTextBlock
import com.slack.api.model.kotlin_extension.block.element.container.MultiBlockElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementRichTextDsl

@BlockLayoutBuilder
class RichTextBlockBuilder private constructor(
        private val elementsContainer: MultiBlockElementContainer
) : Builder<RichTextBlock>, BlockElementRichTextDsl by elementsContainer {
    private var blockId: String? = null

    constructor() : this(MultiBlockElementContainer())

    /**
     * Rich text elements to be rendered inside this block.
     */
    fun elements(builder: BlockElementRichTextDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    /**
     * A string acting as a unique identifier for a block. You can use this block_id when you receive an interaction
     * payload to identify the source of the action. If not specified, one will be generated. Maximum length for this
     * field is 255 characters. block_id should be unique for each message and each iteration of a message. If a
     * message is updated, use a new block_id.
     */
    fun blockId(id: String) {
        blockId = id
    }

    override fun build(): RichTextBlock {
        return RichTextBlock.builder()
                .elements(elementsContainer.underlying)
                .blockId(blockId)
                .build()
    }
}