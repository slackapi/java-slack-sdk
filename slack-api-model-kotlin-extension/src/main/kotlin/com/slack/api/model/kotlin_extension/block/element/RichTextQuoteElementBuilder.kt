package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextQuoteElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiRichTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.RichTextObjectDsl

@BlockLayoutBuilder
class RichTextQuoteElementBuilder private constructor(
    private val elementsContainer: MultiRichTextObjectContainer
) : Builder<RichTextQuoteElement>, RichTextObjectDsl by elementsContainer {
    private var border: Int? = null

    constructor() : this(MultiRichTextObjectContainer())

    /**
     * An array of rich text elements.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block#rich_text_quote">Rich text quote element documentation</a>
     */
    fun elements(builder: RichTextObjectDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    /**
     * Number of pixels of border thickness.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block#rich_text_quote">Rich text quote element documentation</a>
     */
    fun border(border: Int) {
        this.border = border
    }

    override fun build(): RichTextQuoteElement {
        return RichTextQuoteElement.builder()
            .elements(elementsContainer.underlying)
            .border(border)
            .build()
    }
}
