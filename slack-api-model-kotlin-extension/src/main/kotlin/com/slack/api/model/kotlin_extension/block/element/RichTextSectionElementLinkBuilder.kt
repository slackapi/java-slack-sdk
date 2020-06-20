package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextSectionElementLinkBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.Link>, RichTextStyleDsl by styleContainer {
    private var url: String? = null
    private var text: String? = null

    constructor() : this(SingleRichTextStyleContainer())

    /**
     * The URL to redirect to when this link is clicked.
     */
    fun url(url: String) {
        this.url = url
    }

    /**
     * The text shown for the hyperlink.
     */
    fun text(text: String) {
        this.text = text
    }

    override fun build(): RichTextSectionElement.Link {
        return RichTextSectionElement.Link.builder()
                .style(styleContainer.underlying)
                .url(url)
                .text(text)
                .build()
    }
}