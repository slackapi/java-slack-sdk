package com.slack.api.methods.request.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * https://docs.slack.dev/legacy/legacy-steps-from-apps
 */
@Data
@Builder
public class WorkflowsStepFailedRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * A JSON-based object with a message property that should contain a human-readable error message.
     */
    private Map<String, Object> error;

    /**
     * Context identifier that maps to the correct workflow step execution.
     */
    private String workflowStepExecuteId;

}
