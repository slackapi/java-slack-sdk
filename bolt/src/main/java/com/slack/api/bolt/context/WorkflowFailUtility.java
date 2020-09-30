package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepFailedResponse;

import java.io.IOException;
import java.util.Map;

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
