package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.TimePickerElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class TimePickerElementBuilder : Builder<TimePickerElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialTime: String? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _timezone: String? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * A plain_text only text object that defines the placeholder text shown on the timepicker.
     * Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * 	An identifier for the action triggered when a time is selected. You can use this when you receive an
     * 	interaction payload to identify the source of the action. Should be unique among all other action_ids in
     * 	the containing block. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The initial time that is selected when the element is loaded. This should be in the format HH:mm,
     * where HH is the 24-hour format of an hour (00 to 23) and mm is minutes with leading zeros (00 to 59),
     * for example 22:25 for 10:25pm.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun initialTime(time: String) {
        initialTime = time
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a time is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * The timezone to consider for this input value.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun timezone(timezone: String) {
        _timezone = timezone
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#timepicker">Time picker element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): TimePickerElement {
        return TimePickerElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialTime(initialTime)
                .confirm(confirm)
                .focusOnLoad(_focusOnLoad)
                .build()
    }
}
