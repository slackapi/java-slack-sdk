package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowDraftConfiguration;
import com.slack.api.model.workflow.WorkflowPublishedConfiguration;
import lombok.Data;

/**
 * A workflow step supported by your app was removed from a workflow
 * <p>
 * https://docs.slack.dev/reference/events/workflow_step_deleted
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Data
@Deprecated
public class WorkflowStepDeletedEvent implements Event {

    public static final String TYPE_NAME = "workflow_step_deleted";

    private final String type = TYPE_NAME;
    private String workflowId;
    private WorkflowDraftConfiguration workflowDraftConfiguration;
    private WorkflowPublishedConfiguration workflowPublishedConfiguration;
    private String eventTs;
}
