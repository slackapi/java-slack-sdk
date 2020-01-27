package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.setRegular
 */
@Data
@Builder
public class AdminUsersSetRegularRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Workspace Id
     */
    private String teamId;

    /**
     * The ID of the user to designate as a regular user.
     */
    private String userId;

}
