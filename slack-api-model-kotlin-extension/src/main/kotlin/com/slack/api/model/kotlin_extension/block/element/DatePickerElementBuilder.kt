package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.DatePickerElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class DatePickerElementBuilder : Builder<DatePickerElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialDate: String? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * Creates a plain text object in the placeholder field.
     *
     * The placeholder text shown on the datepicker. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datepicker">Date picker element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * 	An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * 	interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * 	elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datepicker">Date picker element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The initial date that is selected when the element is loaded. This should be in the format YYYY-MM-DD.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datepicker">Date picker element documentation</a>
     */
    fun initialDate(date: String) {
        initialDate = date
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a date is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datepicker">Date picker element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datepicker">Date picker element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): DatePickerElement {
        return DatePickerElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialDate(initialDate)
                .confirm(confirm)
                .focusOnLoad(_focusOnLoad)
                .build()
    }
}
