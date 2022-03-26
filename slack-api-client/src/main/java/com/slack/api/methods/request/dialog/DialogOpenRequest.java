package com.slack.api.methods.request.dialog;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.dialog.Dialog;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/dialog.open
 */
@Data
@Builder
public class DialogOpenRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `none`
     */
    private String token;

    /**
     * The dialog definition. This must be a JSON-encoded string.
     */
    private Dialog dialog;

    private String dialogAsString;

    /**
     * Exchange a trigger to post to the user.
     * <p>
     * Apps can invoke dialogs when users interact with slash commands, message buttons,
     * or message menus. Each interaction will include a trigger_id.<p>
     * <p>
     * As apps can only open a dialog in response to such a user action, the
     * {@code trigger_id} is a required parameter.
     *
     * @see <a href="https://api.slack.com/dialogs#implementation">Implementing dialogs</a>
     */
    private String triggerId;
}
