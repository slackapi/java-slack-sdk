package com.slack.api.methods.request.admin.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.workflows.permissions.lookup
 */
@Data
@Builder
public class AdminWorkflowsPermissionsLookupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private List<String> workflowIds;
    private Integer maxWorkflowTriggers;
}
