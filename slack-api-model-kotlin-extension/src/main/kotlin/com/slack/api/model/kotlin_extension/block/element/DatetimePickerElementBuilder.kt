package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.element.DatetimePickerElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class DatetimePickerElementBuilder : Builder<DatetimePickerElement> {
    private var actionId: String? = null
    private var initialDateTime: Int? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * 	An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * 	interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * 	elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datetimepicker">Date time picker element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The initial date and time that is selected when the element is loaded,
     * represented as a UNIX timestamp in seconds. This should be in the format of 10 digits, for example 1628633820 represents the date and time August 10th, 2021 at 03:17pm PST.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datetimepicker">Date time picker element documentation</a>
     */
    fun initialDateTime(datetime: Int) {
        initialDateTime = datetime
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a date is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datetimepicker">Date time picker element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#datetimepicker">Date time picker element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): DatetimePickerElement {
        return DatetimePickerElement.builder()
                .actionId(actionId)
                .initialDateTime(initialDateTime)
                .confirm(confirm)
                .focusOnLoad(_focusOnLoad)
                .build()
    }
}
