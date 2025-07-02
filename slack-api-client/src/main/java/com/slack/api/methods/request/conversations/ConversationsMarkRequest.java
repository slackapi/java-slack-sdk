package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/conversations.mark
 */
@Data
@Builder
public class ConversationsMarkRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Channel or conversation to set the read cursor for.
     */
    private String channel;

    /**
     * Unique identifier of message you want marked as most recently seen in this conversation.
     */
    private String ts;

}