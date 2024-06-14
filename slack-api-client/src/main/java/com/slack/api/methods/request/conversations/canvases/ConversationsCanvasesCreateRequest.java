package com.slack.api.methods.request.conversations.canvases;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.canvas.CanvasDocumentContent;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.canvases.create
 */
@Data
@Builder
public class ConversationsCanvasesCreateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    private String channelId;

    private String markdown;
    private CanvasDocumentContent documentContent;
}