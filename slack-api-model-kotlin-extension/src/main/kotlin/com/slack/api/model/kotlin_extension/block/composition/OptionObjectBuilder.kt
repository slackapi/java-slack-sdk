package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
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
    private var description: PlainTextObject? = null

    constructor() : this(SingleTextObjectContainer())

    /**
     * The string value that will be passed to your app when this option is chosen. Maximum length for this field is
     * 75 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#option">Option object documentation</a>
     */
    fun value(text: String) {
        value = text
    }

    /**
     * A URL to load in the user's browser when the option is clicked. The url attribute is only available in overflow
     * menus. Maximum length for this field is 3000 characters. If you're using url, you'll still receive an
     * interaction payload and will need to send an acknowledgement response.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#option">Option object documentation</a>
     */
    fun url(text: String) {
        url = text
    }

    /**
     * a line of descriptive text shown below the text field beside the radio button. Maximum length for the text
     * object within this field is 75 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#option">Option object documentation</a>
     */
    fun description(text: String, emoji: Boolean? = null) {
        description = PlainTextObject(text, emoji)
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