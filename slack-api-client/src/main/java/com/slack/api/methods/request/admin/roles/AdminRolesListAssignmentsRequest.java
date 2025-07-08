package com.slack.api.methods.request.admin.roles;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.roles.listAssignments
 */
@Data
@Builder
public class AdminRolesListAssignmentsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The entities for which the roles apply
     */
    private List<String> entityIds;

    /**
     * collection of role ids to scope results by
     */
    private List<String> roleIds;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Limit for how many users to be retrieved per page
     */
    private Integer limit;

    /**
     * Sort direction. Default is descending on date_create, can be either ASC or DESC
     */
    private String sortDir;

}
