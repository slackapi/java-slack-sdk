package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowDraftConfiguration;
import lombok.Data;

/**
 * A workflow that contains a step supported by your app was deleted
 *
 * https://api.slack.com/events/workflow_deleted
 */
@Data
public class WorkflowDeletedEvent implements Event {

    public static final String TYPE_NAME = "workflow_deleted";

    private final String type = TYPE_NAME;
    private String workflowId;
    private WorkflowDraftConfiguration workflowDraftConfiguration;
    private String eventTs;
}
