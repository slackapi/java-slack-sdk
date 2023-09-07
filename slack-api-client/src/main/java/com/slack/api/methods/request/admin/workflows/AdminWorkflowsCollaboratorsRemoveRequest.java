package com.slack.api.methods.request.admin.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.workflows.collaborators.remove
 */
@Data
@Builder
public class AdminWorkflowsCollaboratorsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private List<String> collaboratorIds;
    private List<String> workflowIds;
}
