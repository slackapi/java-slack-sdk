package com.slack.api.methods.request.admin.emoji;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.emoji.rename
 */
@Data
@Builder
public class AdminEmojiRenameRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The name of the emoji to be renamed. Colons (:myemoji:) around the value are not required, although they may be included.
     */
    private String name;

    /**
     * The new name of the emoji.
     */
    private String newName;

}
