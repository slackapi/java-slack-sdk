package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.users.list
 */
@Data
@Builder
public class AdminUsersListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The ID (T1234) of the workspace.
     */
    private String teamId;

    /**
     * Only applies with org token and no team_id. If true,
     * return workspaces for a user even if they may be deactivated on them.
     * If false, return workspaces for a user only when user is active on them.
     * Default is false.
     */
    private Boolean includeDeactivatedUserWorkspaces;

    /**
     * If true, only active users will be returned.
     * If false, only deactivated users will be returned. Default is true.
     */
    private Boolean isActive;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Limit for how many users to be retrieved per page
     */
    private Integer limit;

}
