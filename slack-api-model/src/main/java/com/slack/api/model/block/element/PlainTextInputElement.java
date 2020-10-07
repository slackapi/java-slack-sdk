package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.DispatchActionConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#input
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlainTextInputElement extends BlockElement {
    public static final String TYPE = "plain_text_input";
    private final String type = TYPE;

    /**
     * An identifier for the input value when the parent modal is submitted.
     * You can use this when you receive a view_submission payload to identify the value of the input element.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

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
     * Indicates whether the input will be a single line (false) or a larger textarea (true).
     * Defaults to false.
     */
    private boolean multiline;

    /**
     * The minimum length of input that the user must provide. If the user provides less, they will receive an error.
     * Maximum value is 3000.
     */
    private Integer minLength;

    /**
     * The maximum length of input that the user can provide. If the user provides more, they will receive an error.
     */
    private Integer maxLength;

    private DispatchActionConfig dispatchActionConfig;
}
