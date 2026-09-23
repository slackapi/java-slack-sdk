package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.create
 * <p>
 * Create a dedicated code channel for an agent session.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability.
 */
@Data
@Builder
public class AgentsConversationsCreateRequest implements SlackApiRequest {

    private String token;

    /**
     * Encoded team id to create the channel in. Required for org tokens when origin_channel_id is not provided.
     * When omitted, the workspace is derived from the token.
     */
    private String teamId;

    /**
     * An opaque identifier for the agent session. When provided, the call is idempotent: if a channel already exists
     * for this session_id, it is returned instead of creating a new one.
     */
    private String sessionId;

    /**
     * A friendly display name for the code channel. Optional when origin_channel_id and origin_message_ts are
     * provided — in that case the channel name may be derived.
     */
    private String name;

    /**
     * Create a private channel instead of a public one.
     */
    private Boolean isPrivate;

    /**
     * The channel ID where the agent session was initiated from. Must be provided together with origin_message_ts.
     * The channel must be accessible.
     */
    private String originChannelId;

    /**
     * The message timestamp in the origin channel that started the agent session. Must be provided together with
     * origin_channel_id.
     */
    private String originMessageTs;

}
