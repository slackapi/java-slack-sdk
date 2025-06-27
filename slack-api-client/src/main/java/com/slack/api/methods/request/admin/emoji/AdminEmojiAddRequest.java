package com.slack.api.methods.request.admin.emoji;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.emoji.add
 */
@Data
@Builder
public class AdminEmojiAddRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The name of the emoji to be removed. Colons (:myemoji:) around the value are not required, although they may be included.
     */
    private String name;

    /**
     * The URL of a file to use as an image for the emoji. Square images under 128KB and with transparent backgrounds work best.
     */
    private String url;

}
