package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.removeView
 * <p>
 * Remove a view from a code channel. Requires the bot scope {@code code_channels:manage}.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability.
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
