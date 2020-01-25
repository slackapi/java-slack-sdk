package com.github.seratch.jslack.api.methods.request.emoji;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmojiListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `emoji:read`
     */
    private String token;

}