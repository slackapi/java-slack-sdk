package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.InputBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.element.container.SingleBlockElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementInputDsl

@BlockLayoutBuilder
class InputBlockBuilder private constructor(
    private val elementContainer: SingleBlockElementContainer
) : Builder<InputBlock>, BlockElementInputDsl by elementContainer {
    private var blockId: String? = null
    private var label: PlainTextObject? = null
    private var hint: PlainTextObject? = null
    private var optional: Boolean = false
    private var dispatchAction: Boolean? = null

    constructor() : this(SingleBlockElementContainer())

    /**
     * A string acting as a unique identifier for a block. You can use this block_id when you receive an interaction
     * payload to identify the source of the action. If not specified, one will be generated. Maximum length for this
     * field is 255 characters. block_id should be unique for each message and each iteration of a message. If a
     * message is updated, use a new block_id.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * A label that appears above an input element in the form of a text object. Maximum length for the text in this
     * field is 2000 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun label(text: String, emoji: Boolean? = null) {
        label = PlainTextObject(text, emoji)
    }

    /**
     * A plain-text input element, a select menu element, a multi-select menu element, or a datepicker.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun element(builder: BlockElementInputDsl.() -> Unit) {
        elementContainer.apply(builder)
    }

    /**
     * An optional hint that appears below an input element in a lighter grey. Maximum length for the text in this
     * field is 2000 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun hint(text: String, emoji: Boolean? = null) {
        hint = PlainTextObject(text, emoji)
    }

    /**
     * A boolean that indicates whether the input element may be empty when a user submits the modal. Defaults to false.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun optional(optional: Boolean) {
        this.optional = optional
    }

    /**
     * A boolean that indicates whether the input element may be empty when a user submits the modal. Defaults to false.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input block documentation</a>
     */
    fun dispatchAction(dispatchAction: Boolean) {
        this.dispatchAction = dispatchAction
    }

    override fun build(): InputBlock {
        return InputBlock.builder()
            .blockId(blockId)
            .label(label)
            .element(elementContainer.underlying)
            .hint(hint)
            .optional(optional)
            .dispatchAction(dispatchAction)
            .build()
    }
}