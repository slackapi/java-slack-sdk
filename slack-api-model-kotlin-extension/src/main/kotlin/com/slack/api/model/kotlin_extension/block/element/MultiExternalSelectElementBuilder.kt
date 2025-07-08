package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.MultiExternalSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class MultiExternalSelectElementBuilder : Builder<MultiExternalSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialOptions: List<OptionObject>? = null
    private var minQueryLength: Int? = null
    private var maxSelectedItems: Int? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * Adds a plain text object in the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of option objects that exactly match one or more of the options within options or option_groups.
     * These options will be selected when the menu initially loads.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun initialOptions(builder: OptionObjectDsl.() -> Unit) {
        initialOptions = MultiOptionContainer().apply(builder).underlying
    }

    /**
     * When the typeahead field is used, a request will be sent on every character change. If you prefer fewer
     * requests or more fully ideated queries, use the min_query_length attribute to tell Slack the fewest number of
     * typed characters required before dispatch. The default value is 3.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun minQueryLength(length: Int) {
        minQueryLength = length
    }

    /**
     * Specifies the maximum number of items that can be selected in the menu. Minimum number is 1.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun maxSelectedItems(max: Int) {
        maxSelectedItems = max
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears before the multi-select choices are
     * submitted.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): MultiExternalSelectElement {
        return MultiExternalSelectElement.builder()
            .placeholder(placeholder)
            .actionId(actionId)
            .initialOptions(initialOptions)
            .minQueryLength(minQueryLength)
            .maxSelectedItems(maxSelectedItems)
            .confirm(confirm)
            .focusOnLoad(_focusOnLoad)
            .build()
    }
}
