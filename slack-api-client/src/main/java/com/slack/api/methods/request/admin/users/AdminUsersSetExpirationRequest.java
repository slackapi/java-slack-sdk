package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.setExpiration
 */
@Data
@Builder
public class AdminUsersSetExpirationRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The ID (T1234) of the workspace.
     */
    private String teamId;

    /**
     * The ID of the user to set an expiration for.
     */
    private String userId;

    /**
     * Timestamp when guest account should be disabled.
     */
    private Long expirationTs;

}
