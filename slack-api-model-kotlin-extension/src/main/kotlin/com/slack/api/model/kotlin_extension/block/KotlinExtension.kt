package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.kotlin_extension.block.container.MultiLayoutBlockContainer
import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl

// TODO make an extension function on the ChatPostMessageRequestBuilder that invokes this so we can just call it as part of the chain
fun withBlocks(builder: LayoutBlockDsl.() -> Unit): List<LayoutBlock> {
    return MultiLayoutBlockContainer().apply(builder).underlying
}
