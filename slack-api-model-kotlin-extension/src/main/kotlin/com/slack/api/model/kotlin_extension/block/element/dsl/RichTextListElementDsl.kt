package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.RichTextSectionElementBuilder

@BlockLayoutBuilder
interface RichTextListElementDsl {

    fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit)

}
