package com.slack.api.model.kotlin_extension.view

import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.model.view.View.ViewBuilder

fun ViewBuilder.blocks(builder: LayoutBlockDsl.() -> Unit): ViewBuilder {
    return this.blocks(withBlocks(builder))
}