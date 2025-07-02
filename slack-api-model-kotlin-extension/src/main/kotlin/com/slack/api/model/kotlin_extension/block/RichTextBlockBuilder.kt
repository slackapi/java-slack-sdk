package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.RichTextBlock
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

@BlockLayoutBuilder
class RichTextBlockBuilder private constructor(
    private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextBlock>, RichTextElementDsl by elementsContainer {
    private var blockId: String? = null

    constructor() : this(MultiRichTextElementContainer())

    /**
     * A string acting as a unique identifier for a block. You can use this `block_id` when you receive an interaction
     * payload to identify the source of the action. If not specified, one will be generated. Maximum length for this
     * field is 255 characters. `block_id` should be unique for each message and each iteration of a message. If a
     * message is updated, use a new `block_id`.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text block documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * An array of rich text objects - `rich_text_section`, `rich_text_list`, `rich_text_preformatted`,
     * and `rich_text_quote`.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text block documentation</a>
     */
    fun elements(builder: RichTextElementDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    override fun build(): RichTextBlock {
        return RichTextBlock.builder()
            .blockId(blockId)
            .elements(elementsContainer.underlying)
            .build()
    }
}
