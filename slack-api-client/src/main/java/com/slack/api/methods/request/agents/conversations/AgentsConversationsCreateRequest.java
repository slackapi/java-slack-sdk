package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.create
 */
@Data
@Builder
public class AgentsConversationsCreateRequest implements SlackApiRequest {

    private String token;

    /**
     * Encoded team id to create the channel in. Required for org tokens when origin_channel_id is not provided.
     * When omitted, the workspace is derived from the origin channel.
     */
    private String teamId;

    /**
     * An opaque identifier for the agent session. When provided, the call is idempotent: if a channel already exists
     * for this session_id, it is returned instead of creating a new one.
     */
    private String sessionId;

    /**
     * A friendly display name for the code channel. Optional when origin_channel_id and origin_message_ts are
     * provided — in that case the channel name is named from the origin message and re-titled automatically. Required when no origin link is given.
     */
    private String name;

    /**
     * Create a private channel instead of a public one.
     */
    private Boolean isPrivate;

    /**
     * The channel ID where the agent session was initiated from. Must be provided together with origin_message_ts.
     * The channel must be accessible to the calling user and must not be externally shared (Slack Connect). When team_id is omitted with an org token, the channel is created in the same workspace as this origin channel.
     */
    private String originChannelId;

    /**
     * The message timestamp in the origin channel that started the agent session. Must be provided together with
     * origin_channel_id. The author of this message is automatically invited to the newly created code channel.
     */
    private String originMessageTs;

}
