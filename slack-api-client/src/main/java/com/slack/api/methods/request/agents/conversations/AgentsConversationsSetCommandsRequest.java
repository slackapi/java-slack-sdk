package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setCommands
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
     * Full set of commands to register for the calling agent in this channel. This replaces that agent's previously
     * registered set. Pass an empty list to clear the agent's commands. Required.
     */
    private List<Command> commands;

    /**
     * Full set of commands to register for the calling agent in this channel, as a JSON-encoded string array. This
     * replaces that agent's previously registered set. Pass an empty array to clear the agent's commands. Required.
     */
    private String commandsAsString;

    @Data
    @Builder
    public static class Command {
        private String name;
        private String description;
        private String argumentHint;
    }

}
