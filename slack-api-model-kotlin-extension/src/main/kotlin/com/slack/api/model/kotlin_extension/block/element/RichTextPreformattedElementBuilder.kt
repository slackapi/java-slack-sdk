package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextPreformattedElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.MultiRichTextElementContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

@BlockLayoutBuilder
class RichTextPreformattedElementBuilder private constructor(
        private val elementsContainer: MultiRichTextElementContainer
) : Builder<RichTextPreformattedElement>, RichTextElementDsl by elementsContainer {
    constructor() : this(MultiRichTextElementContainer())

    /**
     * Text elements contained in this preformatted text block.
     */
    fun elements(builder: RichTextElementDsl.() -> Unit) {
        elementsContainer.apply(builder)
    }

    override fun build(): RichTextPreformattedElement {
        return RichTextPreformattedElement.builder()
                .elements(elementsContainer.underlying)
                .build()
    }
}