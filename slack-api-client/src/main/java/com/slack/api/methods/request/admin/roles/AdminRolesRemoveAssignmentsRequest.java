package com.slack.api.methods.request.admin.roles;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.roles.removeAssignments
 */
@Data
@Builder
public class AdminRolesRemoveAssignmentsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * ID of the role to which users will be assigned
     */
    private String roleId;

    /**
     * List of the entity IDs for which roles will be revoked. These can be Org IDs, Team IDs or Channel IDs
     */
    private List<String> entityIds;

    /**
     * List of IDs of the users whose roles will be revoked
     */
    private List<String> userIds;

}
