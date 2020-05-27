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

    fun elements(builder: RichTextElementDsl.() -> Unit) {
        this.apply(builder)
    }

    fun style(listStyle: RichTextListStyle) {
        style = listStyle.value
    }

    fun style(listStyle: String) {
        style = listStyle
    }

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