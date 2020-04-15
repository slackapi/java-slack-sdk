package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextPreformattedElement
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

class RichTextPreformattedElementBuilder private constructor(
        private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextPreformattedElement>, RichTextElementDsl by elementsContainer {
    constructor() : this(MultiRichTextElementContainer())

    override fun build(): RichTextPreformattedElement {
        return RichTextPreformattedElement.builder()
                .elements(elementsContainer.underlying)
                .build()
    }
}