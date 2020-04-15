package com.slack.api.model.kotlin_extension.block.dsl

import com.slack.api.model.kotlin_extension.block.*

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface LayoutBlockDsl {
    fun section(builder: SectionBlockBuilder.() -> Unit)
    fun divider(blockId: String? = null)
    fun actions(builder: ActionsBlockBuilder.() -> Unit)
    fun context(builder: ContextBlockBuilder.() -> Unit)
    fun file(blockId: String? = null, externalId: String? = null, source: FileSource? = null)
    fun image(builder: ImageBlockBuilder.() -> Unit)
    fun input(builder: InputBlockBuilder.() -> Unit)
    fun richText(builder: RichTextBlockBuilder.() -> Unit)
}
