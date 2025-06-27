package com.slack.api.model.event;

import com.slack.api.model.workflow.WorkflowPublishedConfiguration;
import lombok.Data;

/**
 * A workflow that contains a step supported by your app was published
 * <p>
 * https://docs.slack.dev/reference/events/workflow_published
 */
@Data
public class WorkflowPublishedEvent implements Event {

    public static final String TYPE_NAME = "workflow_published";

    private final String type = TYPE_NAME;
    private String workflowId;
    private WorkflowPublishedConfiguration workflowPublishedConfiguration;
    private String eventTs;
}
