package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextSectionElementUserBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.User>, RichTextStyleDsl by styleContainer {
    private var userId: String? = null

    constructor() : this(SingleRichTextStyleContainer())

    /**
     * The ID of the user to be referenced.
     */
    fun userId(id: String) {
        userId = id
    }

    override fun build(): RichTextSectionElement.User {
        return RichTextSectionElement.User.builder()
                .style(styleContainer.underlying)
                .userId(userId)
                .build()
    }
}