package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextQuoteElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

@BlockLayoutBuilder
class RichTextQuoteElementBuilder private constructor(
        private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextQuoteElement>, RichTextElementDsl by elementsContainer {
    constructor() : this(MultiRichTextElementContainer())

    override fun build(): RichTextQuoteElement {
        return RichTextQuoteElement.builder()
                .elements(elementsContainer.underlying)
                .build()
    }
}