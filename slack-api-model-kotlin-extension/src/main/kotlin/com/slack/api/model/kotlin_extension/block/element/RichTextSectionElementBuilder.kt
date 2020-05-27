package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

@BlockLayoutBuilder
class RichTextSectionElementBuilder private constructor(
        private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextSectionElement>, RichTextElementDsl by elementsContainer {
    constructor() : this(MultiRichTextElementContainer())

    fun elements(builder: RichTextElementDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    override fun build(): RichTextSectionElement {
        return RichTextSectionElement.builder()
                .elements(elementsContainer.underlying)
                .build()
    }
}