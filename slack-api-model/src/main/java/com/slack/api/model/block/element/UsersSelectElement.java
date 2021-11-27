package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#users_select
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UsersSelectElement extends BlockElement {
    public static final String TYPE = "users_select";
    private final String type = TYPE;

    /**
     * A plain_text only text object that defines the placeholder text shown on the menu.
     * Maximum length for the text in this field is 150 characters.
     */
    private PlainTextObject placeholder;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * The user ID of any valid user to be pre-selected when the menu loads.
     */
    private String initialUser;

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     */
    private ConfirmationDialogObject confirm;

    /**
     * Indicates whether the element will be set to auto focus within the view object.
     * Only one element can be set to true. Defaults to false.
     */
    private Boolean focusOnLoad;
}
