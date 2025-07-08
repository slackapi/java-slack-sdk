package com.slack.api.methods.request.admin.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.workflows.collaborators.add
 */
@Data
@Builder
public class AdminWorkflowsCollaboratorsAddRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private List<String> collaboratorIds;
    private List<String> workflowIds;
}
