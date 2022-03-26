package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.leave
 */
@Data
@Builder
public class ConversationsLeaveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * Conversation to leave
     */
    private String channel;

}
