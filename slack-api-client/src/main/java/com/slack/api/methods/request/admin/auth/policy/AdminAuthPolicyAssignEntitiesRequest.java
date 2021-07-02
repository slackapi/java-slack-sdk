package com.slack.api.methods.request.admin.auth.policy;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.auth.policy.assignEntities
 */
@Data
@Builder
public class AdminAuthPolicyAssignEntitiesRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Array of IDs to assign to the policy.
     */
    private List<String> entityIds;

    private String policyName;

    /**
     * The name of the authentication policy to assign the entities to.
     * Currently, email_password is the only policy that may be used with this method.
     */
    private String entityType;
}
