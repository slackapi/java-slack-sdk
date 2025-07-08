package com.slack.api.model.block.element;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.composition.DispatchActionConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/number-input-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NumberInputElement extends BlockElement {
    public static final String TYPE = "number_input";
    private final String type = TYPE;

    /**
     * An identifier for the input value when the parent modal is submitted.
     * You can use this when you receive a view_submission payload to identify the value of the input element.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * Decimal numbers are allowed if is_decimal_allowed= true, set the value to false otherwise.
     */
    @SerializedName("is_decimal_allowed")
    private boolean decimalAllowed;

    /**
     * A plain_text only text object that defines the placeholder text shown in the plain-text input.
     * Maximum length for the text in this field is 150 characters.
     */
    private PlainTextObject placeholder;

    /**
     * The initial value in the plain-text input when it is loaded.
     */
    private String initialValue;

    /**
     * The minimum value, cannot be greater than max_value.
     */
    private String minValue;

    /**
     * The maximum value, cannot be less than min_value.
     */
    private String maxValue;

    /**
     * A dispatch configuration object that determines
     * when during text input the element returns a block_actions payload.
     */
    private DispatchActionConfig dispatchActionConfig;

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     */
    private Boolean focusOnLoad;
}
