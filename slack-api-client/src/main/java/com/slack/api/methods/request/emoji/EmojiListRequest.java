package com.slack.api.methods.request.emoji;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/emoji.list
 */
@Data
@Builder
public class EmojiListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `emoji:read`
     */
    private String token;

    /**
     * Include a list of categories for Unicode emoji and the emoji in each category
     */
    private Boolean includeCategories;

}