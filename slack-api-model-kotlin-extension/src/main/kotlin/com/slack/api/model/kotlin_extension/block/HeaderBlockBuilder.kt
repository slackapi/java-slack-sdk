package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.HeaderBlock
import com.slack.api.model.block.composition.PlainTextObject

@BlockLayoutBuilder
class HeaderBlockBuilder : Builder<HeaderBlock> {
    private var blockId: String? = null
    private var _text: PlainTextObject? = null

    fun blockId(id: String) {
        blockId = id
    }

    fun text(text: String, emoji: Boolean? = null) {
        _text = PlainTextObject(text, emoji)
    }

    override fun build(): HeaderBlock {
        return HeaderBlock.builder()
            .blockId(blockId)
            .text(_text)
            .build()
    }
}