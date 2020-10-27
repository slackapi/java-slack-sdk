package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.session.list
 */
@Data
@Builder
public class AdminUsersSessionListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. (admin.users:read)
     */
    private String token;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * The maximum number of items to return. Must be between 1 - 1000 both inclusive.
     * <p>
     * Default: 1000
     */
    private Integer limit;

    /**
     * ID of the team you'd like active sessions for (must be used with user_id)
     */
    private String teamId;

    /**
     * ID of user you'd like active sessions for (must be used with team_id)
     */
    private String userId;

}