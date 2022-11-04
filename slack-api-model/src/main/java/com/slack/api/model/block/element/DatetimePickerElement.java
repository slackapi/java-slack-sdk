package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#datetimepicker
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DatetimePickerElement extends BlockElement {
    public static final String TYPE = "datetimepicker";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * The initial date and time that is selected when the element is loaded,
     * represented as a UNIX timestamp in seconds. This should be in the format of 10 digits, for example 1628633820 represents the date and time August 10th, 2021 at 03:17pm PST.
     */
    private Integer initialDateTime;

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
