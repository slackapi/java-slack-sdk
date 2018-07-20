package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsSetPurposeRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * Conversation to set the purpose of
     */
    private String channel;

    /**
     * A new, specialer purpose
     */
    private String purpose;

}
