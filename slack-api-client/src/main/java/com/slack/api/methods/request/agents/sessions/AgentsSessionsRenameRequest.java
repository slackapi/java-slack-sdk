package com.slack.api.methods.request.agents.sessions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.sessions.rename
 */
@Data
@Builder
public class AgentsSessionsRenameRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the channel containing the agent session.
     */
    private String channelId;

    /**
     * New title for the agent session (1-200 characters). For a session channel, this also renames the channel.
     */
    private String title;

    /**
     * Timestamp of the thread root message the session is scoped to. Required for thread-based sessions in regular
     * channels and DMs. Must be omitted for session channels.
     */
    private String threadTs;

}
