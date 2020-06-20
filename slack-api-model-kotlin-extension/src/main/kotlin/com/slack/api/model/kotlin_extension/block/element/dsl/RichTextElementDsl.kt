package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.*

@BlockLayoutBuilder
interface RichTextElementDsl {
    // I'm providing some nonverbose overloads which just take the text data and no styling data, as this is potentially a common use case
    /**
     * Defines plain text and additional text styling.
     */
    fun text(builder: RichTextSectionElementTextBuilder.() -> Unit)

    /**
     * Defines some plain text without any styling.
     */
    fun text(value: String)

    /**
     * Defines a link to a channel with text styling.
     */
    fun channel(builder: RichTextSectionElementChannelBuilder.() -> Unit)

    /**
     * Defines a link to a channel without any styling.
     */
    fun channel(channelId: String)

    /**
     * Defines a link to a user with text styling.
     */
    fun user(builder: RichTextSectionElementUserBuilder.() -> Unit)

    /**
     * Defines a link to a user without any styling.
     */
    fun user(userId: String)

    /**
     * Defines an emoji with text styling.
     */
    fun emoji(builder: RichTextSectionElementEmojiBuilder.() -> Unit)

    /**
     * Defines an emoji without any text styling.
     */
    fun emoji(emojiName: String)

    /**
     * Defines a link to an external webpage.
     */
    fun link(builder: RichTextSectionElementLinkBuilder.() -> Unit)

    /**
     * Defines a link to another slack team with text styling.
     */
    fun team(builder: RichTextSectionElementTeamBuilder.() -> Unit)

    /**
     * Defines a link to another slack team without any text styling.
     */
    fun team(teamId: String)

    /**
     * Defines a link to a group of users with a given [user group][usergroupId]
     */
    fun usergroup(usergroupId: String)

    /**
     * Defines a formatted date.
     */
    fun date(timestamp: String)

    /**
     * Defines a broadcast mention such as @channel
     */
    fun broadcast(range: String)

    fun color(value: String)

    /**
     * Defines an ordered or unordered text list.
     */
    fun richTextList(builder: RichTextListElementBuilder.() -> Unit)

    /**
     * Defines a preformatted text block.
     */
    fun richTextPreformatted(builder: RichTextPreformattedElementBuilder.() -> Unit)

    /**
     * Defines a quote block.
     */
    fun richTextQuote(builder: RichTextQuoteElementBuilder.() -> Unit)

    /**
     * Defines a section of text.
     */
    fun richTextSection(builder: RichTextSectionElementBuilder.() -> Unit)
}