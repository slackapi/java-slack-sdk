package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowStepExecution;
import lombok.Data;

/**
 * A workflow step supported by your app should execute
 * <p>
 * https://docs.slack.dev/reference/events/workflow_step_execute
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Data
@Deprecated
public class WorkflowStepExecuteEvent implements Event {

    public static final String TYPE_NAME = "workflow_step_execute";

    private final String type = TYPE_NAME;
    private String callbackId;
    private WorkflowStepExecution workflowStep;
    private String eventTs;
}