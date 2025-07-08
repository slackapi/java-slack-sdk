package com.slack.api.methods.request.admin.emoji;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.emoji.remove
 */
@Data
@Builder
public class AdminEmojiRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The name of the emoji to be removed. Colons (:myemoji:) around the value are not required, although they may be included.
     */
    private String name;

}
