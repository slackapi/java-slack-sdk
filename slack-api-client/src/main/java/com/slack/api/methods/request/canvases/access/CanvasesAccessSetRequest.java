package com.slack.api.methods.request.canvases.access;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/canvases.access.set
 */
@Data
@Builder
public class CanvasesAccessSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String canvasId;
    private String accessLevel;
    private List<String> channelIds;
    private List<String> userIds;
}