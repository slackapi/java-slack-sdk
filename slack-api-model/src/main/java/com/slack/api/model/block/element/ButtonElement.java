package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#button
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ButtonElement extends BlockElement {
    public static final String TYPE = "button";
    private final String type = TYPE;

    /**
     * A text object that defines the button's text. Can only be of type: plain_text.
     * Maximum length for the text in this field is 75 characters.
     */
    private PlainTextObject text;

    /**
     * An identifier for this action. You can use this when you receive an interaction payload
     * to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * A URL to load in the user's browser when the button is clicked.
     * Maximum length for this field is 3000 characters.
     * If you're using url, you'll still receive an interaction payload and will need to send an acknowledgement response.
     */
    private String url;

    /**
     * The value to send along with the interaction payload.
     * Maximum length for this field is 2000 characters.
     */
    private String value;

    /**
     * Decorates buttons with alternative visual color schemes.
     * Use this option with restraint.
     * <p>
     * `primary` gives buttons a green outline and text, ideal for affirmation or confirmation actions.
     * `primary` should only be used for one button within a set.
     * <p>
     * `danger` gives buttons a red outline and text, and should be used when the action is destructive.
     * Use danger even more sparingly than `primary`.
     * <p>
     * If you don't include this field, the `default` button style will be used.
     */
    private String style; // possible values: primary, danger

    /**
     * A confirm object that defines an optional confirmation dialog after the button is clicked.
     */
    private ConfirmationDialogObject confirm;

    /**
     * A label for longer descriptive text about a button element.
     * This label will be read out by screen readers instead of the button text object.
     * Maximum length for this field is 75 characters.
     */
    private String accessibilityLabel;
}
