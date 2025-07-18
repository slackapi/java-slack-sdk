package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#conversation_multi_select
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConversationsSelectElement extends BlockElement {
    public static final String TYPE = "conversations_select";
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
     * The ID of any valid conversation to be pre-selected when the menu loads.
     */
    private String initialConversation;

    /**
     * Pre-populates the select menu with the conversation that the user was viewing when they opened the modal,
     * if available. If initial_conversation is also supplied, it will be ignored. Default is false.
     */
    private Boolean defaultToCurrentConversation;

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     */
    private ConfirmationDialogObject confirm;

    /**
     * This field only works with menus in input blocks in modals.
     * When set to true, the view_submission payload from the menu's parent view will contain a response_url.
     * This response_url can be used for message responses.
     * The target conversation for the message will be determined by the value of this select menu.
     */
    private Boolean responseUrlEnabled;

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     */
    private ConversationsFilter filter;

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     */
    private Boolean focusOnLoad;
}
