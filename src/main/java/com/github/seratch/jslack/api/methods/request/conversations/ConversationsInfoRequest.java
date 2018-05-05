package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    /**
     * Conversation ID to learn more about
     */
    private String channel;

    /**
     * Set this to `true` to receive the locale for this conversation. Defaults to `false`
     */
    private boolean includeLocale;

}