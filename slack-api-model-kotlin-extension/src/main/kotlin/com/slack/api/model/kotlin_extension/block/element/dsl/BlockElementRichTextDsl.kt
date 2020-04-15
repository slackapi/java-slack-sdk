package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.element.RichTextListElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextPreformattedElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextQuoteElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextSectionElementBuilder

interface BlockElementRichTextDsl {
    fun richTextList(builder: RichTextListElementBuilder.() -> Unit)

    fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit)

    fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit)

    fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit)
}