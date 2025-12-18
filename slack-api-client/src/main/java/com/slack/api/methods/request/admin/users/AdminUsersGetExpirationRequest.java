package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.users.getExpiration/
 */
@Data
@Builder
public class AdminUsersGetExpirationRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The ID of the user to add to the workspace.
     */
    private String userId;

    /**
     * Workspace Id.
     */
    private String targetTeam;
}

