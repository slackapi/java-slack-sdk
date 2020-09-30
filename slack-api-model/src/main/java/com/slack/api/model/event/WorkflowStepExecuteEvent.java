package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowStepExecution;
import lombok.Data;

/**
 * A workflow step supported by your app should execute
 * <p>
 * https://api.slack.com/events/workflow_step_execute
 */
@Data
public class WorkflowStepExecuteEvent implements Event {

    public static final String TYPE_NAME = "workflow_step_execute";

    private final String type = TYPE_NAME;
    private String callbackId;
    private WorkflowStepExecution workflowStep;
    private String eventTs;
}