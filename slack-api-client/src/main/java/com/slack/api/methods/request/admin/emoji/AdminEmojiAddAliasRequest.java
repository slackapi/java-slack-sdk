package com.slack.api.methods.request.admin.emoji;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.emoji.addAlias
 */
@Data
@Builder
public class AdminEmojiAddAliasRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The alias of the emoji.
     */
    private String aliasFor;

    /**
     * The name of the emoji to be aliased. Colons (:myemoji:) around the value are not required, although they may be included.
     */
    private String name;

}
