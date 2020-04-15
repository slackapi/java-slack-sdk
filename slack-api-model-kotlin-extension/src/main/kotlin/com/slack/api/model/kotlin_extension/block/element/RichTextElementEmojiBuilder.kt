package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextElementEmojiBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.Emoji>, RichTextStyleDsl by styleContainer {
    private var name: String? = null
    private var skinTone: Int? = null

    constructor() : this(SingleRichTextStyleContainer())

    fun name(name: String) {
        this.name = name
    }

    fun skinTone(tone: Int) {
        skinTone = tone
    }

    override fun build(): RichTextSectionElement.Emoji {
        return RichTextSectionElement.Emoji.builder()
                .style(styleContainer.underlying)
                .name(name)
                .skinTone(skinTone)
                .build()
    }
}