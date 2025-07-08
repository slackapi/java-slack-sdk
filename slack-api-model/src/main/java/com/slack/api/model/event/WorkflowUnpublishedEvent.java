package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowDraftConfiguration;
import lombok.Data;

/**
 * A workflow that contains a step supported by your app was unpublished
 * <p>
 * https://docs.slack.dev/reference/events/workflow_unpublished
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Data
@Deprecated
public class WorkflowUnpublishedEvent implements Event {

    public static final String TYPE_NAME = "workflow_unpublished";

    private final String type = TYPE_NAME;
    private String workflowId;
    private WorkflowDraftConfiguration workflowDraftConfiguration;
    private String eventTs;
}
