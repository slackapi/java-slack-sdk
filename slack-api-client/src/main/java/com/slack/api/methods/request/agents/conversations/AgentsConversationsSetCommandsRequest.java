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
     * Full set of commands to register for the calling agent in this channel, replacing that agent's previously
     * registered set. Pass an empty array to clear the agent's commands. At most 10 commands may exist across all
     * agents in the channel; names must be unique within the set and must not collide with builtin Slack commands.
     */
    private List<Command> commands;

    @Data
    @Builder
    public static class Command {
        private String name;
        private String description;
        private String argumentHint;
    }

}
