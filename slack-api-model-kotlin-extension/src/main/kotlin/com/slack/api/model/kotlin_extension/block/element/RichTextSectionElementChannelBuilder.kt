package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextSectionElementChannelBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.Channel>, RichTextStyleDsl by styleContainer {
    private var channelId: String? = null

    constructor() : this(SingleRichTextStyleContainer())

    /**
     * The ID of the channel to reference.
     */
    fun channelId(id: String) {
        channelId = id
    }

    override fun build(): RichTextSectionElement.Channel {
        return RichTextSectionElement.Channel.builder()
                .style(styleContainer.underlying)
                .channelId(channelId)
                .build()
    }
}