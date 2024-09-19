package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.block.element.RichTextSectionElement.LimitedTextStyle
import com.slack.api.model.block.element.RichTextSectionElement.TextStyle
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.BroadcastRange

@BlockLayoutBuilder
interface RichTextObjectDsl {

    /**
     * @param range The range of the broadcast.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#broadcast-element-type">Rich text object documentation</a>
     */
    fun broadcast(range: BroadcastRange, style: LimitedTextStyle? = null)

    /**
     * @param value The hex value for the color.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#color-element-type">Rich text object documentation</a>
     */
    fun color(value: String, style: LimitedTextStyle? = null)

    /**
     * @param channelId The ID of the channel to be mentioned.
     * @param style An object of six optional boolean properties that dictate style: bold, italic, strike, highlight, client_highlight, and unlink.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#channel-element-type">Rich text object documentation</a>
     */
    fun channel(channelId: String, style: LimitedTextStyle? = null)

    /**
     * @param timestamp A Unix timestamp for the date to be displayed in seconds.
     * @param format A template string containing curly-brace-enclosed tokens to substitute your provided timestamp.
     * @param url URL to link the entire format string to.
     * @param fallback Text to display in place of the date should parsing, formatting or displaying fail.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#date-element-type">Rich text object documentation</a>
     */
    fun date(timestamp: Int, format: String, style: TextStyle? = null, url: String? = null, fallback: String? = null)

    /**
     * @param name The name of the emoji; i.e. "wave" or "wave::skin-tone-2".
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#emoji-element-type">Rich text object documentation</a>
     */
    fun emoji(name: String, skinTone: Int? = null, style: LimitedTextStyle? = null)

    /**
     * @param url The link's url.
     * @param text The text shown to the user (instead of the url). If no text is provided, the url is used.
     * @param unsafe Indicates whether the link is safe.
     * @param style An object containing four boolean properties: bold, italic, strike, and code.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#link-element-type">Rich text object documentation</a>
     */
    fun link(url: String, text: String? = null, unsafe: Boolean? = null, style: TextStyle? = null)

    /**
     * @param text The text shown to the user.
     * @param style An object containing four boolean fields, none of which are required: bold, italic, strike, and code.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#text-element-type">Rich text object documentation</a>
     */
    fun text(text: String, style: TextStyle? = null)

    /**
     * @param userId The ID of the user to be mentioned.
     * @param style An object of six optional boolean properties that dictate style: bold, italic, strike, highlight, client_highlight, and unlink.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#user-element-type">Rich text object documentation</a>
     */
    fun user(userId: String, style: LimitedTextStyle? = null)

    /**
     * @param usergroupId The ID of the user group to be mentioned.
     * @param style An object of six optional boolean properties that dictate style: bold, italic, strike, highlight, client_highlight, and unlink.
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#user-group-element-type">Rich text object documentation</a>
     */
    fun usergroup(usergroupId: String, style: LimitedTextStyle? = null)

}
