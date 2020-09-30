package com.slack.api.model.workflow;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/events/workflow_step_execute
 */
@Data
public class WorkflowStepExecution {
    private String workflowStepExecuteId;
    private String workflowId;
    private String workflowInstanceId;
    private String stepId;
    private Map<String, WorkflowStepInput> inputs;
    private List<WorkflowStepOutput> outputs;
}
