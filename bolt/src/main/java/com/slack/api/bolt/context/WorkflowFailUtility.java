package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.workflows.WorkflowsStepFailedResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Deprecated
public interface WorkflowFailUtility {

    String getWorkflowStepExecuteId();

    MethodsClient client();

    default WorkflowsStepFailedResponse fail(Map<String, Object> error) throws IOException, SlackApiException {
        WorkflowsStepFailedResponse response = client().workflowsStepFailed(r -> r
                .workflowStepExecuteId(getWorkflowStepExecuteId())
                .error(error)
        );
        return response;
    }

}
