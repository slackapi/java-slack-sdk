package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.removeView
 */
@Data
@Builder
public class AgentsConversationsRemoveViewRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to remove the view from.
     */
    private String channelId;

    /**
     * Agent-assigned key of the view to remove. Provide exactly one of view_key or view_id.
     */
    private String viewKey;

    /**
     * Encoded channel tab ID of the view to remove. Provide exactly one of view_key or view_id.
     */
    private String viewId;

}
