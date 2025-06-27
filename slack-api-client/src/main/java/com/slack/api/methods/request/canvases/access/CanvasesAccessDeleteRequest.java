package com.slack.api.methods.request.canvases.access;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/canvases.access.delete
 */
@Data
@Builder
public class CanvasesAccessDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String canvasId;
    private List<String> channelIds;
    private List<String> userIds;
}