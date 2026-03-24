package com.slack.api.methods.request.apps.user.connection;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.update
 */
@Data
@Builder
public class AppsUserConnectionUpdateRequest implements SlackApiRequest {

    private String token;

    /**
     * The user ID to update the connection status for.
     */
    private String userId;

    /**
     * The connection status to set.
     */
    private String status;
}
