package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionGroupObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MultiStaticSelectElement extends BlockElement {
    public static final String TYPE = "multi_static_select";
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
     * An array of option objects. Maximum number of options is 100.
     * If option_groups is specified, this field should not be.
     * <p>
     * NOTE: The reason I didn't initialize the List<> fields is because Slack (sometimes) gives errors
     * when it encounters an empty list in the generated JSON.
     * The proper solution if/when you don't want un-initialized fields is to have a Gson type adapter that skips empty lists
     *
     * @see <a href="https://github.com/slackapi/java-slack-sdk/pull/103">The Pull request #103</a>
     * @see <a href="https://stackoverflow.com/questions/11942118/how-do-you-get-gson-to-omit-null-or-empty-objects-and-empty-arrays-and-lists">A related discussion on StackOverFlow.com</a>
     */
    private List<OptionObject> options;

    /**
     * An array of option group objects. Maximum number of option groups is 100.
     * If options is specified, this field should not be.
     * <p>
     * NOTE: The reason I didn't initialize the List<> fields is because Slack (sometimes) gives errors
     * when it encounters an empty list in the generated JSON.
     * The proper solution if/when you don't want un-initialized fields is to have a Gson type adapter that skips empty lists
     *
     * @see <a href="https://github.com/slackapi/java-slack-sdk/pull/103">The Pull request #103</a>
     * @see <a href="https://stackoverflow.com/questions/11942118/how-do-you-get-gson-to-omit-null-or-empty-objects-and-empty-arrays-and-lists">A related discussion on StackOverFlow.com</a>
     */
    private List<OptionGroupObject> optionGroups;

    /**
     * An array of option objects that exactly match one or more of the options within options or option_groups.
     * These options will be selected when the menu initially loads.
     */
    private List<OptionObject> initialOptions;

    /**
     * A confirm object that defines an optional confirmation dialog that appears before the multi-select choices are submitted.
     */
    private ConfirmationDialogObject confirm;

    /**
     * Specifies the maximum number of items that can be selected in the menu. Minimum number is 1.
     */
    private Integer maxSelectedItems;

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     */
    private Boolean focusOnLoad;
}
