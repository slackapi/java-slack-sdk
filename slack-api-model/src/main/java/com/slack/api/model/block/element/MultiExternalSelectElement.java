package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/reference/block-kit/block-elements#external_multi_select
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultiExternalSelectElement extends BlockElement {
    public static final String TYPE = "multi_external_select";
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
     * An array of option objects that exactly match one or more of the options within options or option_groups.
     * These options will be selected when the menu initially loads.
     */
    private List<OptionObject> initialOptions;

    /**
     * When the typeahead field is used, a request will be sent on every character change.
     * If you prefer fewer requests or more fully ideated queries, use the min_query_length attribute
     * to tell Slack the fewest number of typed characters required before dispatch.
     */
    private Integer minQueryLength;

    /**
     * A confirm object that defines an optional confirmation dialog
     * that appears before the multi-select choices are submitted.
     */
    private ConfirmationDialogObject confirm;

    /**
     * Specifies the maximum number of items that can be selected in the menu.
     * Minimum number is 1.
     */
    private Integer maxSelectedItems;

}
