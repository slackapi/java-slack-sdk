package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/chat.stopStream
 */
@Data
@Builder
public class ChatStopStreamRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * An encoded ID that represents a channel, private group, or DM.
     */
    private String channel;

    /**
     * The timestamp of the streaming message.
     */
    private String ts;

    /**
     * Accepts message text formatted in markdown. Limit this field to 12,000 characters.
     */
    private String markdownText;

    /**
     * JSON object with event_type and event_payload fields, presented as a URL-encoded string.
     * Metadata you post to Slack is accessible to any app or user who is a member of that workspace.
     */
    private Message.Metadata metadata;

    /**
     * JSON object with event_type and event_payload fields, presented as a URL-encoded string.
     * Metadata you post to Slack is accessible to any app or user who is a member of that workspace.
     */
    private String metadataAsString;

    /**
     * A JSON-based array of structured blocks that will be rendered at the bottom of the finalized message, presented as a URL-encoded string.
     */
    private List<LayoutBlock> blocks;

    /**
     * A JSON-based array of structured blocks as a String that will be rendered at the bottom of the finalized message, presented as a URL-encoded string.
     */
    private String blocksAsString;
}
