package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.archive
 * <p>
 * Archive a code channel. Requires the bot scope {@code code_channels:manage}.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability.
 */
@Data
@Builder
public class AgentsConversationsArchiveRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to archive.
     */
    private String channelId;

    /**
     * Timestamp of a message in the code channel to share back as a thread reply on the origin message. Requires the
     * channel to have an origin link.
     */
    private String summaryMessageTs;

}
