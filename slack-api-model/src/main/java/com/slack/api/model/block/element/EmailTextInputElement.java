package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.DispatchActionConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/email-input-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailTextInputElement extends BlockElement {
    public static final String TYPE = "email_text_input";
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
     * The initial value in the email input when it is loaded.
     */
    private String initialValue;

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
