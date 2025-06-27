package com.slack.api.methods.request.canvases;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.CallParticipant;
import com.slack.api.model.canvas.CanvasDocumentContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/canvases.create
 */
@Data
@Builder
public class CanvasesCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String title;

    // You can pass either markdown or documentContent
    private String markdown;
    private CanvasDocumentContent documentContent;
}