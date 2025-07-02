package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.approved.list
 */
@Data
@Builder
public class AdminAppsApprovedListRequest implements SlackApiRequest {

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

    /**
     * Limit the results to only include certified apps. When false, no certified apps will appear in the result
     */
    private Boolean certified;

}
