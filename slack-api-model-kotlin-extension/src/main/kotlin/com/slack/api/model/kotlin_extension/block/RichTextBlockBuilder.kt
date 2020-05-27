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

    fun elements(builder: BlockElementRichTextDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

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