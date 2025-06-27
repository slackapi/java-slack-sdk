package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/time-picker-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimePickerElement extends BlockElement {
    public static final String TYPE = "timepicker";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a time is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids in the containing block.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * A plain_text only text object that defines the placeholder text shown on the timepicker.
     * Maximum length for the text in this field is 150 characters.
     */
    private PlainTextObject placeholder;

    /**
     * The initial time that is selected when the element is loaded.
     * This should be in the format HH:mm, where HH is the 24-hour format of an hour (00 to 23)
     * and mm is minutes with leading zeros (00 to 59), for example 22:25 for 10:25pm.
     */
    private String initialTime;

    /**
     * The timezone to consider for this input value.
     */
    private String timezone;

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a date is selected.
     */
    private ConfirmationDialogObject confirm;

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     */
    private Boolean focusOnLoad;
}
