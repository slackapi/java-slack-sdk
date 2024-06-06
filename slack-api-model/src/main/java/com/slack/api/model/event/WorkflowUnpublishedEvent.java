package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowDraftConfiguration;
import lombok.Data;

/**
 * A workflow that contains a step supported by your app was unpublished
 * <p>
 * https://api.slack.com/events/workflow_unpublished
 * @deprecated Use new custom steps: https://api.slack.com/automation/functions/custom-bolt
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
