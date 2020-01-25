package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.apps.restricted.list
 */
@Data
@Builder
public class AdminAppsRestrictedListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page
     */
    private String cursor;

    /**
     * The maximum number of items to return. Must be between 1 - 1000 both inclusive.
     */
    private Integer limit;

    /**
     * Org Id.
     * <p>
     * Note: enterprise_id and team_id cannot be used together.
     * Passing enterprise_id will return the list of org-wide approved apps.
     * Passing team_id will return the apps approved for that specific workspace.
     */
    private String enterpriseId;

    /**
     * Workspace Id.
     * <p>
     * Note: enterprise_id and team_id cannot be used together.
     * Passing enterprise_id will return the list of org-wide approved apps.
     * Passing team_id will return the apps approved for that specific workspace.
     */
    private String teamId;

}
