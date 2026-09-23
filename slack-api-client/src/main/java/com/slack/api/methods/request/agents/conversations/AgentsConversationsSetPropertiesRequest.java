package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setProperties
 * <p>
 * Set properties on a code channel.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability. The {@code code_channel} and
 * {@code agent_resource} object arguments are passed as JSON-encoded strings until their shapes are stabilized.
 */
@Data
@Builder
public class AgentsConversationsSetPropertiesRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to update.
     */
    private String channelId;

    /**
     * New display title for the agent session.
     */
    private String title;

    /**
     * New status for the agent session.
     */
    private String status;

    /**
     * Code channel properties to set, as a JSON-encoded string. Only provided fields are updated.
     */
    private String codeChannelAsString;

    /**
     * Agent resource properties to set, as a JSON-encoded string. Only provided fields are updated.
     */
    private String agentResourceAsString;

}
