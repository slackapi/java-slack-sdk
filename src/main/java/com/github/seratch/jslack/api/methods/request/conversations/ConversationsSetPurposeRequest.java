package com.github.seratch.jslack.api.methods.request.conversations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsSetPurposeRequest {

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
