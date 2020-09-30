package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;
import com.slack.api.model.workflow.WorkflowStepOutput;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
