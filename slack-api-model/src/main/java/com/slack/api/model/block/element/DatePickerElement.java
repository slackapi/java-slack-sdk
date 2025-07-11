package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/date-picker-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DatePickerElement extends BlockElement {
    public static final String TYPE = "datepicker";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * A plain_text only text object that defines the placeholder text shown on the datepicker.
     * Maximum length for the text in this field is 150 characters.
     */
    private PlainTextObject placeholder;

    /**
     * The initial date that is selected when the element is loaded. This should be in the format YYYY-MM-DD.
     */
    private String initialDate;

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
