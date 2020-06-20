package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.element.container.SingleRichTextStyleContainer
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

@BlockLayoutBuilder
class RichTextSectionElementTeamBuilder private constructor(
        private val styleContainer: SingleRichTextStyleContainer
) : Builder<RichTextSectionElement.Team>, RichTextStyleDsl by styleContainer {
    private var teamId: String? = null

    constructor() : this(SingleRichTextStyleContainer())

    /**
     * The ID of the referenced team.
     */
    fun teamId(id: String) {
        teamId = id
    }

    override fun build(): RichTextSectionElement.Team {
        return RichTextSectionElement.Team.builder()
                .style(styleContainer.underlying)
                .teamId(teamId)
                .build()
    }
}