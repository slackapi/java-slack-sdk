package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ExternalSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder

@BlockLayoutBuilder
class ExternalSelectElementBuilder : Builder<ExternalSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialOption: OptionObject? = null
    private var minQueryLength: Int? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * Adds a plain text object to the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * 	A single option that exactly matches one of the options within the options or option_groups loaded from the
     * 	external data source. This option will be selected when the menu initially loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    /**
     * When the typeahead field is used, a request will be sent on every character change. If you prefer fewer
     * requests or more fully ideated queries, use the min_query_length attribute to tell Slack the fewest number of
     * typed characters required before dispatch. The default value is 3.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun minQueryLength(length: Int) {
        minQueryLength = length
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#external_select">External select element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): ExternalSelectElement {
        return ExternalSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialOption(initialOption)
                .minQueryLength(minQueryLength)
                .confirm(confirm)
                .focusOnLoad(_focusOnLoad)
                .build()
    }
}
