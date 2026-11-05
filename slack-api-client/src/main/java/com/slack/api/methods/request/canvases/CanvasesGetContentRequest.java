package com.slack.api.methods.request.canvases;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/canvases.getContent
 */
@Data
@Builder
public class CanvasesGetContentRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Encoded ID of the canvas
     */
    private String canvasId;

    /**
     * Format in which to return the canvas content. Defaults to markdown.
     * Acceptable values: `html`, `markdown`
     */
    private String contentType;
}