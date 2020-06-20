package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextListElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextPreformattedElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextQuoteElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextSectionElementBuilder

@BlockLayoutBuilder
interface BlockElementRichTextDsl {
    /**
     * Defines an ordered or unordered text list.
     */
    fun richTextList(builder: RichTextListElementBuilder.() -> Unit)

    /**
     * Defines a preformatted text block.
     */
    fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit)

    /**
     * Defines a quote block.
     */
    fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit)

    /**
     * Defines a section of text.
     */
    fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit)
}