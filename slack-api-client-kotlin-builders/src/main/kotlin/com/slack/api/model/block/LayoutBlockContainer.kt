package com.slack.api.model.block

import com.slack.api.SlackAPIBuilder

/**
 * Interface for adding one to many layout block elements to this container.
 */
@SlackAPIBuilder
interface LayoutBlockContainer {
    fun section(blockID: String? = null, buildSection: SectionBlockBuilder.() -> Unit)
    fun divider(blockID: String? = null)
    fun actions(blockID: String? = null, buildActions: ActionsBlockBuilder.() -> Unit)
    // TODO add more layout block methods later if POC is successful
}

/**
 * Supports a LayoutBlockContainer that can have one to many layout block elements.
 */
class MultiLayoutBlockContainerImpl : LayoutBlockContainer {
    val layoutBlocks = mutableListOf<LayoutBlock>()

    override fun section(blockID: String?, buildSection: SectionBlockBuilder.() -> Unit) {
        layoutBlocks += SectionBlockBuilder(blockID).apply(buildSection).build()
    }
    override fun divider(blockID: String?) {
        layoutBlocks += DividerBlock(blockID)
    }
    override fun actions(blockID: String?, buildActions: ActionsBlockBuilder.() -> Unit) {
        layoutBlocks += ActionsBlockBuilder(blockID).apply(buildActions).build()
    }
}