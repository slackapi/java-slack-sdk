package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.getCanvas
 * <p>
 * Fetch a canvas attached to a code channel. Requires the bot scope {@code code_channels:manage}.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability.
 */
@Data
@Builder
public class AgentsConversationsGetCanvasRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the agent session channel the canvas belongs to. Note: this method uses the "channel" argument, not
     * "channel_id".
     */
    private String channel;

    /**
     * Encoded ID of the canvas to fetch.
     */
    private String canvasId;

    /**
     * Format to render the canvas content in. Defaults to markdown.
     */
    private String contentFormat;

    /**
     * Whether to include resolved comment threads in the response. Defaults to false.
     */
    private Boolean includeResolved;

}
