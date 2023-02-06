package com.slack.api.methods.request.admin.roles;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.roles.addAssignments
 */
@Data
@Builder
public class AdminRolesAddAssignmentsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;


    /**
     * ID of the role to which users will be assigned.
     */
    private String roleId;

    /**
     * List of the entity IDs for which roles will be assigned. These can be Org IDs, Team IDs or Channel IDs
     */
    private List<String> entityIds;

    /**
     * List of IDs from the users to be added to the given role
     */
    private List<String> userIds;
}
