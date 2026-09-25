package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setCanvasContent
 * <p>
 * Replace the full markdown content of a plan canvas attached to a code channel.
 */
@Data
@Builder
public class AgentsConversationsSetCanvasContentRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the agent session channel the canvas is attached to.
     */
    private String channel;

    /**
     * Encoded ID of the canvas whose content to replace.
     */
    private String canvasId;

    /**
     * The full new canvas content as markdown. The server diffs this against the current content and applies only the
     * changed sections.
     */
    private String content;

}
