package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextListElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

@BlockLayoutBuilder
class RichTextListElementBuilder private constructor(
        private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextListElement>, RichTextElementDsl by elementsContainer {
    private var style: String? = null
    private var indent: Int? = null

    constructor() : this(MultiRichTextElementContainer())

    /**
     * The list items to render.
     */
    fun elements(builder: RichTextElementDsl.() -> Unit) {
        this.apply(builder)
    }

    /**
     * The list style, e.g. "bulleted".
     *
     * This implementation uses a type safe enum for the list style.
     */
    fun style(listStyle: RichTextListStyle) {
        style = listStyle.value
    }

    /**
     * The list style, e.g. "bulleted".
     *
     * This implementation uses a string for the list style. This may be preferable if a new
     * list style is introduced and the enum is not sufficient.
     */
    fun style(listStyle: String) {
        style = listStyle
    }

    /**
     * The indent level of the list items.
     */
    fun indent(level: Int) {
        indent = level
    }

    override fun build(): RichTextListElement {
        return RichTextListElement.builder()
                .elements(elementsContainer.underlying)
                .style(style)
                .indent(indent)
                .build()
    }
}