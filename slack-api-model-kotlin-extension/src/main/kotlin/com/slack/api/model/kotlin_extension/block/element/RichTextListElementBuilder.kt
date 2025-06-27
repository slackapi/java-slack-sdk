package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextListElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextSectionElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextListElementDsl

@BlockLayoutBuilder
class RichTextListElementBuilder private constructor(
    private val elementsContainer: MultiRichTextSectionElementContainer
) : Builder<RichTextListElement>, RichTextListElementDsl by elementsContainer {
    private var style: String? = null
    private var indent: Int? = null
    private var offset: Int? = null
    private var border: Int? = null

    constructor() : this(MultiRichTextSectionElementContainer())

    /**
     * Either bullet or ordered, the latter meaning a numbered list.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text list element documentation</a>
     */
    fun style(style: ListStyle) {
        this.style = style.value
    }

    /**
     * An array of rich_text_section objects containing two properties: type, which is "rich_text_section",
     * and elements, which is an array of rich text element objects.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text list element documentation</a>
     */
    fun elements(builder: RichTextListElementDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    /**
     * Number of pixels to indent the list.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text list element documentation</a>
     */
    fun indent(indent: Int) {
        this.indent = indent
    }

    /**
     * Number of pixels to offset the list.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text list element documentation</a>
     */
    fun offset(offset: Int) {
        this.offset = offset
    }

    /**
     * Number of pixels of border thickness.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text list element documentation</a>
     */
    fun border(border: Int) {
        this.border = border
    }

    override fun build(): RichTextListElement {
        return RichTextListElement.builder()
            .style(style)
            .elements(elementsContainer.underlying)
            .indent(indent)
            .offset(offset)
            .border(border)
            .build()
    }
}
