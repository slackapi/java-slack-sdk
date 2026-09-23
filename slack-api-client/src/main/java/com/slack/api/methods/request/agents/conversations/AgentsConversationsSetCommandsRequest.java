package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setCommands
 * <p>
 * Register the set of agent-defined slash commands for the calling agent in a code channel. Requires the bot scope
 * {@code code_channels:manage}.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability. The {@code commands} array is passed as a
 * JSON-encoded string until the command item shape is stabilized.
 */
@Data
@Builder
public class AgentsConversationsSetCommandsRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to register commands for.
     */
    private String channelId;

    /**
     * Full set of commands to register for the calling agent in this channel, as a JSON-encoded string array. This
     * replaces that agent's previously registered set. Pass an empty array to clear the agent's commands. Required.
     */
    private String commandsAsString;

}
