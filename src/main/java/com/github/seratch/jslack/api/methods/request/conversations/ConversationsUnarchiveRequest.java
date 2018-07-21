package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsUnarchiveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * ID of conversation to unarchive
     */
    private String channel;
}
