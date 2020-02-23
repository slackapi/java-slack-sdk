package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionGroupObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/reference/messaging/block-elements#static-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticSelectElement extends BlockElement {
    public static final String TYPE = "static_select";
    private final String type = TYPE;

    private PlainTextObject placeholder;
    private String actionId;

    /**
     * An array of option objects.
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
     * An array of option group objects.
     * <p>
     * NOTE: The reason I didn't initialize the List<> fields is because Slack (sometimes) gives errors
     * when it encounters an empty list in the generated JSON.
     * The proper solution if/when you don't want un-initialized fields is to have a Gson type adapter that skips empty lists
     *
     * @see <a href="https://github.com/slackapi/java-slack-sdk/pull/103">The Pull request #103</a>
     * @see <a href="https://stackoverflow.com/questions/11942118/how-do-you-get-gson-to-omit-null-or-empty-objects-and-empty-arrays-and-lists">A related discussion on StackOverFlow.com</a>
     */
    private List<OptionGroupObject> optionGroups;

    private OptionObject initialOption;
    private ConfirmationDialogObject confirm;
}
