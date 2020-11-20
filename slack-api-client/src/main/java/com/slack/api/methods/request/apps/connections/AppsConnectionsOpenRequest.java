package com.slack.api.methods.request.apps.connections;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/apps.connections.open
 */
@Data
@Builder
public class AppsConnectionsOpenRequest implements SlackApiRequest {

    /**
     * app-level token (connections:write required)
     */
    private String token;
}