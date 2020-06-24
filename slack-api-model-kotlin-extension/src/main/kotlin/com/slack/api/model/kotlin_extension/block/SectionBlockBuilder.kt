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

    /**
     * 	A string acting as a unique identifier for a block. You can use this block_id when you receive an interaction
     * 	payload to identify the source of the action. If not specified, one will be generated. Maximum length for
     * 	this field is 255 characters. block_id should be unique for each message and each iteration of a message.
     * 	If a message is updated, use a new block_id.
     *
     * 	@see <a href="https://api.slack.com/reference/block-kit/blocks#section">Section Block Documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * An array of text objects. Any text objects included with fields will be rendered in a compact format that
     * allows for 2 columns of side-by-side text. Maximum number of items is 10. Maximum length for the text in each
     * item is 2000 characters.
     *
     * 	@see <a href="https://api.slack.com/reference/block-kit/blocks#section">Section Block Documentation</a>
     */
    fun fields(builder: TextObjectDsl.() -> Unit) {
        fields = MultiTextObjectContainer().apply(builder).underlying
    }

    /**
     * 	One of the available element objects.
     *
     * 	@see BlockElementDsl for the available accessories that can be constructed
     * 	@see <a href="https://api.slack.com/reference/block-kit/blocks#section">Section Block Documentation</a>
     */
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
