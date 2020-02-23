package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionObject;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/reference/block-kit/block-elements#overflow
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverflowMenuElement extends BlockElement {
    public static final String TYPE = "overflow";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * hould be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * An array of option objects to display in the menu. Maximum number of options is 5, minimum is 2.
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
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     */
    private ConfirmationDialogObject confirm;
}
