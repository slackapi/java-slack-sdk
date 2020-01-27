package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.assign
 */
@Data
@Builder
public class AdminUsersAssignRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Workspace Id.
     */
    private String teamId;

    /**
     * The ID of the user to add to the workspace.
     */
    private String userId;

    /**
     * True if user should be added to the workspace as a guest.
     */
    private boolean isRestricted;

    /**
     * True if user should be added to the workspace as a single-channel guest.
     */
    private boolean isUltraRestricted;

}
