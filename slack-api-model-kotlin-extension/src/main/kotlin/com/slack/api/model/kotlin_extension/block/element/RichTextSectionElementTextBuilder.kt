package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextSectionElementTextBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.Text>, RichTextStyleDsl by styleContainer {
    private var text: String? = null

    constructor() : this(SingleRichTextStyleContainer())

    /**
     * The rendered text.
     */
    fun text(containedText: String) {
        text = containedText
    }

    override fun build(): RichTextSectionElement.Text {
        return RichTextSectionElement.Text.builder()
                .style(styleContainer.underlying)
                .text(text)
                .build()
    }
}