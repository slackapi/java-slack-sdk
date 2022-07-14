package com.slack.api.model.kotlin_extension.block.container

import com.slack.api.model.block.DividerBlock
import com.slack.api.model.block.FileBlock
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.kotlin_extension.block.*
import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl

/**
 * Supports a LayoutBlockContainer that can have one to many layout block elements.
 */
class MultiLayoutBlockContainer : LayoutBlockDsl {
    val underlying = mutableListOf<LayoutBlock>()

    override fun section(builder: SectionBlockBuilder.() -> Unit) {
        underlying += SectionBlockBuilder().apply(builder).build()
    }

    override fun header(builder: HeaderBlockBuilder.() -> Unit) {
        underlying += HeaderBlockBuilder().apply(builder).build()
    }

    override fun divider(blockId: String?) {
        underlying += DividerBlock(blockId)
    }

    override fun actions(builder: ActionsBlockBuilder.() -> Unit) {
        underlying += ActionsBlockBuilder().apply(builder).build()
    }

    override fun context(builder: ContextBlockBuilder.() -> Unit) {
        underlying += ContextBlockBuilder().apply(builder).build()
    }

    override fun file(externalId: String?, blockId: String?, source: FileSource?) {
        underlying += FileBlock.builder()
                .blockId(blockId)
                .externalId(externalId)
                .source(source?.value)
                .build()
    }

    override fun file(externalId: String?, blockId: String?, source: String?) {
        underlying += FileBlock.builder()
                .blockId(blockId)
                .externalId(externalId)
                .source(source)
                .build()
    }

    override fun image(builder: ImageBlockBuilder.() -> Unit) {
        underlying += ImageBlockBuilder().apply(builder).build()
    }

    override fun input(builder: InputBlockBuilder.() -> Unit) {
        underlying += InputBlockBuilder().apply(builder).build()
    }

    override fun video(builder: VideoBlockBuilder.() -> Unit) {
        underlying += VideoBlockBuilder().apply(builder).build()
    }
}