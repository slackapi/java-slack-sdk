package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.workflows.WorkflowsUpdateStepResponse;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @deprecated Use new custom steps: https://docs.slack.dev/workflows/workflow-steps
 */
@Deprecated
public interface WorkflowUpdateUtility {

    String getWorkflowStepEditId();

    MethodsClient client();

    default WorkflowsUpdateStepResponse update(
            Map<String, WorkflowStepInput> inputs,
            List<WorkflowStepOutput> outputs
    ) throws IOException, SlackApiException {
        return update(inputs, outputs, null, null);
    }

    default WorkflowsUpdateStepResponse update(
            Map<String, WorkflowStepInput> inputs,
            List<WorkflowStepOutput> outputs,
            String stepName,
            String stepImageUrl
    ) throws IOException, SlackApiException {
        WorkflowsUpdateStepResponse response = client().workflowsUpdateStep(r -> r
                .workflowStepEditId(getWorkflowStepEditId())
                .inputs(inputs)
                .outputs(outputs)
                .stepName(stepName)
                .stepImageUrl(stepImageUrl)
        );
        return response;
    }

}
