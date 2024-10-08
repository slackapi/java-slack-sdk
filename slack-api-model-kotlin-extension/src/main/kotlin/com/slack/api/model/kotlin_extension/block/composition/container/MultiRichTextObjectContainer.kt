package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.element.RichTextElement
import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.block.element.RichTextSectionElement.LimitedTextStyle
import com.slack.api.model.block.element.RichTextSectionElement.TextStyle
import com.slack.api.model.kotlin_extension.block.composition.dsl.RichTextObjectDsl
import com.slack.api.model.kotlin_extension.block.element.BroadcastRange

class MultiRichTextObjectContainer : RichTextObjectDsl {
    val underlying = mutableListOf<RichTextElement>()

    override fun broadcast(range: BroadcastRange, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.Broadcast.builder()
            .range(range.value)
            .style(style)
            .build()
    }

    override fun color(value: String, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.Color.builder()
            .value(value)
            .style(style)
            .build()
    }

    override fun channel(channelId: String, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.Channel.builder()
            .channelId(channelId)
            .style(style)
            .build()
    }

    override fun date(timestamp: Int, format: String, style: TextStyle?, url: String?, fallback: String?) {
        underlying += RichTextSectionElement.Date.builder()
            .timestamp(timestamp)
            .format(format)
            .style(style)
            .url(url)
            .fallback(fallback)
            .build()
    }

    override fun emoji(name: String, skinTone: Int?, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.Emoji.builder()
            .name(name)
            .skinTone(skinTone)
            .style(style)
            .build()
    }

    override fun link(url: String, text: String?, unsafe: Boolean?, style: TextStyle?) {
        underlying += RichTextSectionElement.Link.builder()
            .url(url)
            .text(text)
            .unsafe(unsafe)
            .style(style)
            .build()
    }

    override fun text(text: String, style: TextStyle?) {
        underlying += RichTextSectionElement.Text.builder()
            .text(text)
            .style(style)
            .build()
    }

    override fun user(userId: String, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.User.builder()
            .userId(userId)
            .style(style)
            .build()
    }

    override fun usergroup(usergroupId: String, style: LimitedTextStyle?) {
        underlying += RichTextSectionElement.UserGroup.builder()
            .usergroupId(usergroupId)
            .style(style)
            .build()
    }
}
