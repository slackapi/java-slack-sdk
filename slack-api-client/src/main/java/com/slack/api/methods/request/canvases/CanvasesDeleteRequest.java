package com.slack.api.methods.request.canvases;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/canvases.delete
 */
@Data
@Builder
public class CanvasesDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String canvasId;
}