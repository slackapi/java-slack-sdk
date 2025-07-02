package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.WorkflowConfigureUtility;
import com.slack.api.bolt.context.Context;
import lombok.*;

/**
 * workflow_step_edit type request's context.
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
public class WorkflowStepEditContext extends Context implements WorkflowConfigureUtility {

    private String triggerId;
    private String callbackId;

}
