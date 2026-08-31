package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ContainerBlock
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.RichTextBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.block.element.ImageElement
import com.slack.api.model.kotlin_extension.block.container.MultiLayoutBlockContainer

@BlockLayoutBuilder
class ContainerBlockBuilder : Builder<ContainerBlock> {
    private var blockId: String? = null
    private var title: TextObject? = null
    private var richTextTitle: RichTextBlock? = null
    private var subtitle: TextObject? = null
    private var childBlocks: List<LayoutBlock>? = null
    private var width: String? = null
    private var icon: ImageElement? = null
    private var isCollapsible: Boolean? = null
    private var defaultCollapsed: Boolean? = null
    private var hasHeaderDivider: Boolean? = null

    fun blockId(id: String) {
        blockId = id
    }

    fun title(text: String, emoji: Boolean? = null) {
        title = PlainTextObject(text, emoji)
    }

    fun title(textObject: TextObject) {
        title = textObject
    }

    fun richTextTitle(richText: RichTextBlock) {
        richTextTitle = richText
    }

    fun subtitle(text: String, emoji: Boolean? = null) {
        subtitle = PlainTextObject(text, emoji)
    }

    fun subtitle(textObject: TextObject) {
        subtitle = textObject
    }

    fun childBlocks(builder: MultiLayoutBlockContainer.() -> Unit) {
        childBlocks = MultiLayoutBlockContainer().apply(builder).underlying
    }

    fun width(width: String) {
        this.width = width
    }

    fun icon(imageUrl: String, altText: String) {
        icon = ImageElement.builder().imageUrl(imageUrl).altText(altText).build()
    }

    fun isCollapsible(collapsible: Boolean) {
        isCollapsible = collapsible
    }

    fun defaultCollapsed(collapsed: Boolean) {
        defaultCollapsed = collapsed
    }

    fun hasHeaderDivider(divider: Boolean) {
        hasHeaderDivider = divider
    }

    override fun build(): ContainerBlock {
        return ContainerBlock.builder()
            .blockId(blockId)
            .title(title)
            .richTextTitle(richTextTitle)
            .subtitle(subtitle)
            .childBlocks(childBlocks ?: ArrayList())
            .width(width)
            .icon(icon)
            .isCollapsible(isCollapsible)
            .defaultCollapsed(defaultCollapsed)
            .hasHeaderDivider(hasHeaderDivider)
            .build()
    }
}
