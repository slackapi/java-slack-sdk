package com.slack.api.model.kotlin_extension.block.element.container

import com.slack.api.model.block.element.RichTextElement
import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.element.*
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextElementDsl

class MultiRichTextElementContainer : RichTextElementDsl {
    val underlying = mutableListOf<RichTextElement>()

    override fun text(builder: RichTextElementTextBuilder.() -> Unit) {
        underlying += RichTextElementTextBuilder().apply(builder).build()
    }

    override fun text(value: String) {
        underlying += RichTextElementTextBuilder().apply {
            text(value)
        }.build()
    }

    override fun channel(builder: RichTextElementChannelBuilder.() -> Unit) {
        underlying += RichTextElementChannelBuilder().apply(builder).build()
    }

    override fun channel(channelId: String) {
        underlying += RichTextElementChannelBuilder().apply {
            channelId(channelId)
        }.build()
    }

    override fun user(builder: RichTextElementUserBuilder.() -> Unit) {
        underlying += RichTextElementUserBuilder().apply(builder).build()
    }

    override fun user(userId: String) {
        underlying += RichTextElementUserBuilder().apply {
            userId(userId)
        }.build()
    }

    override fun emoji(builder: RichTextElementEmojiBuilder.() -> Unit) {
        underlying += RichTextElementEmojiBuilder().apply(builder).build()
    }

    override fun emoji(emojiName: String) {
        underlying += RichTextElementEmojiBuilder().apply {
            name(emojiName)
        }.build()
    }

    override fun link(builder: RichTextElementLinkBuilder.() -> Unit) {
        underlying += RichTextElementLinkBuilder().apply(builder).build()
    }

    override fun team(builder: RichTextElementTeamBuilder.() -> Unit) {
        underlying += RichTextElementTeamBuilder().apply(builder).build()
    }

    override fun team(teamId: String) {
        underlying += RichTextElementTeamBuilder().apply {
            teamId(teamId)
        }.build()
    }

    override fun usergroup(usergroupId: String) {
        underlying += RichTextSectionElement.UserGroup.builder()
                .usergroupId(usergroupId)
                .build()
    }

    override fun date(timestamp: String) {
        underlying += RichTextSectionElement.Date.builder()
                .timestamp(timestamp)
                .build()
    }

    override fun broadcast(range: String) {
        underlying += RichTextSectionElement.Broadcast.builder()
                .range(range)
                .build()
    }

    override fun color(value: String) {
        underlying += RichTextSectionElement.Color.builder()
                .value(value)
                .build()
    }

    override fun richTextList(builder: RichTextListElementBuilder.() -> Unit) {
        underlying += RichTextListElementBuilder().apply(builder).build()
    }

    override fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit) {
        underlying += RichTextPreformattedElementBuilder().apply(builder).build()
    }

    override fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit) {
        underlying += RichTextQuoteElementBuilder().apply(builder).build()
    }

    override fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit) {
        underlying += RichTextSectionElementBuilder().apply(builder).build()
    }
}