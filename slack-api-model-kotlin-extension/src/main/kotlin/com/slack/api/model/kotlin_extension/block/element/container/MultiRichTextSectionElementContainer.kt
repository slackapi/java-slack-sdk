package com.slack.api.model.kotlin_extension.block.element.container

import com.slack.api.model.block.element.RichTextElement
import com.slack.api.model.kotlin_extension.block.element.RichTextSectionElementBuilder
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextListElementDsl

/**
 * Supports a RichTextSectionElementContainer that can hold one to many rich text section elements
 */
class MultiRichTextSectionElementContainer : RichTextListElementDsl {
    val underlying = mutableListOf<RichTextElement>()

    override fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit) {
        underlying += RichTextSectionElementBuilder().apply(builder).build()
    }
}
