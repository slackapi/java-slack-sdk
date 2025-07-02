package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.WorkflowCompleteUtility;
import com.slack.api.bolt.context.WorkflowFailUtility;
import lombok.*;

/**
 * workflow_step_execute type request's context.
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Deprecated
public class WorkflowStepExecuteContext extends Context
        implements WorkflowCompleteUtility, WorkflowFailUtility {
    private String callbackId;
    private String workflowStepExecuteId;
}
