package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.kotlin_extension.block.container.MultiLayoutBlockContainer
import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl

/**
 * Starts a block builder DSL to build elements with the blocks kit.
 */
fun withBlocks(builder: LayoutBlockDsl.() -> Unit): List<LayoutBlock> {
    return MultiLayoutBlockContainer().apply(builder).underlying
}
