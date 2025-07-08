package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiRichTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.RichTextObjectDsl

@BlockLayoutBuilder
class RichTextSectionElementBuilder private constructor(
    private val elementsContainer: MultiRichTextObjectContainer
) : Builder<RichTextSectionElement>, RichTextObjectDsl by elementsContainer {
    constructor() : this(MultiRichTextObjectContainer())

    /**
     * An array of rich text elements.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block#rich_text_section">Rich text section element documentation</a>
     */
    fun elements(builder: RichTextObjectDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    override fun build(): RichTextSectionElement {
        return RichTextSectionElement.builder()
            .elements(elementsContainer.underlying)
            .build()
    }
}
