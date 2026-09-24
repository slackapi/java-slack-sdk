package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.container.SingleTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class OptionObjectBuilder private constructor(
    private val textContainer: SingleTextObjectContainer
) : Builder<OptionObject>, TextObjectDsl by textContainer {
    private var value: String? = null
    private var url: String? = null
    private var description: TextObject? = null

    constructor() : this(SingleTextObjectContainer())

    /**
     * The string value that will be passed to your app when this option is chosen. Maximum length for this field is
     * 75 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/option-object">Option object documentation</a>
     */
    fun value(text: String) {
        value = text
    }

    /**
     * A URL to load in the user's browser when the option is clicked. The url attribute is only available in overflow
     * menus. Maximum length for this field is 3000 characters. If you're using url, you'll still receive an
     * interaction payload and will need to send an acknowledgement response.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/option-object">Option object documentation</a>
     */
    fun url(text: String) {
        url = text
    }

    /**
     * A plain_text text object that defines a line of descriptive text shown below the text field beside a single
     * selectable item in a select menu, multi-select menu, checkbox group, radio button group, or overflow menu.
     * Maximum length for the text within this field is 75 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/option-object">Option object documentation</a>
     */
    fun description(text: String, emoji: Boolean? = null) {
        description = PlainTextObject(text, emoji)
    }

    /**
     * A mrkdwn text object that defines a line of descriptive text shown below the text field beside a single
     * selectable item. Only checkbox group and radio button group items can use mrkdwn formatting.
     * Maximum length for the text within this field is 75 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/option-object">Option object documentation</a>
     */
    fun markdownDescription(text: String, verbatim: Boolean? = null) {
        description = MarkdownTextObject(text, verbatim)
    }

    override fun build(): OptionObject {
        return OptionObject.builder()
            .description(description)
            .text(textContainer.underlying)
            .value(value)
            .url(url)
            .build()
    }
}