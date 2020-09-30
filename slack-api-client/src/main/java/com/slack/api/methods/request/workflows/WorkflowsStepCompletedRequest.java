package com.slack.api.methods.request.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class WorkflowsStepCompletedRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Context identifier that maps to the correct workflow step execution.
     */
    private String workflowStepExecuteId;

    /**
     * Key-value object of outputs from your step.
     * Keys of this object reflect the configured key properties of
     * your outputs array from your workflow_step object.
     */
    private Map<String, Object> outputs;
    private String outputsAsString;

}
