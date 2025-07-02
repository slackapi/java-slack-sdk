package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Deprecated
public interface WorkflowCompleteUtility {

    String getWorkflowStepExecuteId();

    MethodsClient client();

    // NOTE: workflows.stepCompleted usually takes 3+ seconds.
    // Unlike others, please consider running this method asynchronously.
    default WorkflowsStepCompletedResponse complete(Map<String, Object> outputs) throws IOException, SlackApiException {
        WorkflowsStepCompletedResponse response = client().workflowsStepCompleted(r -> r
                .workflowStepExecuteId(getWorkflowStepExecuteId())
                .outputs(outputs)
        );
        return response;
    }

}
