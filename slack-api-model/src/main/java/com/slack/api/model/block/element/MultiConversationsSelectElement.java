package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultiConversationsSelectElement extends BlockElement {
    public static final String TYPE = "multi_conversations_select";
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
     * An array of one or more IDs of any valid conversations to be pre-selected when the menu loads.
     */
    private List<String> initialConversations;

    /**
     * A confirm object that defines an optional confirmation dialog that appears
     * before the multi-select choices are submitted.
     */
    private ConfirmationDialogObject confirm;

    /**
     * Specifies the maximum number of items that can be selected in the menu.
     * Minimum number is 1.
     */
    private Integer maxSelectedItems;

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     */
    private ConversationsFilter filter;

}
