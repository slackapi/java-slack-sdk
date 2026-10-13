package com.slack.api.methods.request.apps.user.connection;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.bulkUpdate
 */
@Data
@Builder
public class AppsUserConnectionBulkUpdateRequest implements SlackApiRequest {

    private String token;

    /**
     * The user IDs to mark as connected.
     */
    private List<String> connectedUserIds;

    /**
     * The user IDs to mark as disconnected.
     */
    private List<String> disconnectedUserIds;
}
