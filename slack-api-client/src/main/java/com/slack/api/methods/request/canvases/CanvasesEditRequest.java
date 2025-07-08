package com.slack.api.methods.request.canvases;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.canvas.CanvasDocumentChange;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/canvases.edit
 */
@Data
@Builder
public class CanvasesEditRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String canvasId;
    private List<CanvasDocumentChange> changes;
}