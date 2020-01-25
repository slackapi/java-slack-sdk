package com.github.seratch.jslack.api.methods.request.admin.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.list
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
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Limit for how many users to be retrieved per page
     */
    private Integer limit;

}
