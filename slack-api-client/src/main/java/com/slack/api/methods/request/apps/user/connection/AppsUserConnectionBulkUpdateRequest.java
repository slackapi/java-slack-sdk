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
     * At least one of connectedUserIds or disconnectedUserIds is required; combined they may not exceed 1000 users.
     */
    private List<String> connectedUserIds;

    /**
     * The user IDs to mark as disconnected.
     * At least one of connectedUserIds or disconnectedUserIds is required; combined they may not exceed 1000 users.
     */
    private List<String> disconnectedUserIds;
}
