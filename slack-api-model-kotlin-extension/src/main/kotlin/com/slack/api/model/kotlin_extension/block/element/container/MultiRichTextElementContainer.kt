package com.slack.api.model.kotlin_extension.block.element.container

import com.slack.api.model.block.element.BlockElement
import com.slack.api.model.kotlin_extension.block.element.RichTextListElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextPreformattedElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextQuoteElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextSectionElementBuilder
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

/**
 * Supports a RichTextElementContainer that can hold one to many rich text elements
 */
class MultiRichTextElementContainer : RichTextElementDsl {
    val underlying = mutableListOf<BlockElement>()

    override fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit) {
        underlying += RichTextSectionElementBuilder().apply(builder).build()
    }

    override fun richTextList(builder: RichTextListElementBuilder.() -> Unit) {
        underlying += RichTextListElementBuilder().apply(builder).build()
    }

    override fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit) {
        underlying += RichTextPreformattedElementBuilder().apply(builder).build()
    }

    override fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit) {
        underlying += RichTextQuoteElementBuilder().apply(builder).build()
    }
}
