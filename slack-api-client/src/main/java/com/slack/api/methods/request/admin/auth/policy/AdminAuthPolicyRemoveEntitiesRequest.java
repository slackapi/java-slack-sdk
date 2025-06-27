package com.slack.api.methods.request.admin.auth.policy;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.auth.policy.removeEntities
 */
@Data
@Builder
public class AdminAuthPolicyRemoveEntitiesRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Encoded IDs of the entities you'd like to remove from the policy.
     */
    private List<String> entityIds;

    /**
     * The name of the policy to remove entities from. Currently, email_password is the only policy
     * that may be used with this method.
     */
    private String policyName;

    private String entityType;

}
