package com.slack.api.methods.request.agents.sessions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.sessions.setStatus
 */
@Data
@Builder
public class AgentsSessionsSetStatusRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the channel containing the agent session.
     */
    private String channelId;

    /**
     * The lifecycle status to set. Acceptable values: active, processing, suspended, closed.
     */
    private String status;

    /**
     * Timestamp of the thread root message the session is scoped to. Required for thread-based sessions in regular
     * channels and DMs. Must be omitted for session channels.
     */
    private String threadTs;

    /**
     * Title for the agent session (max 200 characters). Only used when creating a new session; ignored if the session
     * already exists. To rename an existing session, use agents.sessions.rename.
     */
    private String title;

    /**
     * The user who initiated the session. Only used when creating a new session; ignored if the session already
     * exists. Must be a member of the channel.
     */
    private String initiatorUserId;

    /**
     * Emoji to use as the agent's icon. Takes priority over icon_url. Remains in effect until you clear it (pass null)
     * or set a new value. Requires the chat:write.customize scope.
     */
    private String iconEmoji;

    /**
     * URL to an image to use as the agent's icon. Remains in effect until you clear it (pass null) or set a new value.
     * Requires the chat:write.customize scope.
     */
    private String iconUrl;

    /**
     * Display name override for the agent (max 200 characters). Remains in effect until you clear it (pass null) or set
     * a new value. Requires the chat:write.customize scope.
     */
    private String username;

}
