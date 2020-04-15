package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.*

@BlockLayoutBuilder
interface RichTextElementDsl {
    // I'm providing some nonverbose overloads which just take the text data and no styling data, as this is potentially a common use case
    fun text(builder: RichTextElementTextBuilder.() -> Unit)
    fun text(value: String)

    fun channel(builder: RichTextElementChannelBuilder.() -> Unit)
    fun channel(channelId: String)

    fun user(builder: RichTextElementUserBuilder.() -> Unit)
    fun user(userId: String)

    fun emoji(builder: RichTextElementEmojiBuilder.() -> Unit)
    fun emoji(emojiName: String)

    fun link(builder: RichTextElementLinkBuilder.() -> Unit)

    fun team(builder: RichTextElementTeamBuilder.() -> Unit)
    fun team(teamId: String)

    fun usergroup(usergroupId: String)

    fun date(timestamp: String)

    fun broadcast(range: String)

    fun color(value: String)

    fun richTextList(builder: RichTextListElementBuilder.() -> Unit)

    fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit)

    fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit)

    fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit)
}