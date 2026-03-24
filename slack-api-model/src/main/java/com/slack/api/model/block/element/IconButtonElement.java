package com.slack.api.model.block.element;

import com.slack.api.model.Confirmation;
import com.slack.api.model.block.ContextActionsBlockElement;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;

import lombok.*;

import java.util.List;

/**
 * An icon button to perform actions.
 * https://docs.slack.dev/reference/block-kit/block-elements/icon-button-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IconButtonElement extends BlockElement implements ContextActionsBlockElement {
    public static final String TYPE = "icon_button";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * The icon to show (e.g., "trash").
     */
    private String icon;

    /*
     * A text object that defines the button's text. Can only be of type: plain_text.
     */
    private PlainTextObject text;

    /**
     * The value to send along with the interaction payload. Maximum length is 2000 characters.
     */
    private String value;

    /**
     * A confirm object that defines an optional confirmation dialog after the button is clicked.
     */
    private ConfirmationDialogObject confirm;

    /**
     * A label for longer descriptive text about a button element. This label will be read out by screen readers instead of the button text object. Maximum length is 75 characters.
     */
    private String accessibilityLabel;

    /**
     * An array of user IDs for which the icon button appears. If not provided, the button is visible to all users.
     */
    private List<String> visibleToUserIds;
}
