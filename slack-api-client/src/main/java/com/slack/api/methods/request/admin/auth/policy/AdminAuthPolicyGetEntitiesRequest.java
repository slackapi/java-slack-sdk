package com.slack.api.methods.request.admin.auth.policy;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.auth.policy.getEntities
 */
@Data
@Builder
public class AdminAuthPolicyGetEntitiesRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String entityType;

    /**
     * The name of the policy to fetch entities for. Currently, email_password is the only policy
     * that may be used with this method.
     */
    private String policyName;

    /**
     * The maximum number of items to return. Must be between 1 and 1000, both inclusive.
     * Default: 1000
     */
    private Integer limit;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;
}
