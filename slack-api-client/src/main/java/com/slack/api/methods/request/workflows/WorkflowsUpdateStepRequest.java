package com.slack.api.methods.request.workflows;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/methods/workflows.updateStep
 */
@Data
@Builder
public class WorkflowsUpdateStepRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * A context identifier provided with view_submission payloads
     * used to call back to workflows.updateStep.
     */
    private String workflowStepEditId;

    /**
     * A JSON key-value map of inputs required from a user during configuration.
     * This is the data your app expects to receive when the workflow step starts.
     * Please note: the embedded variable format is set and replaced by the workflow system.
     * You cannot create custom variables that will be replaced at runtime.
     */
    private Map<String, WorkflowStepInput> inputs;
    private String inputsAsString;

    /**
     * A JSON array of output objects used during step execution.
     * This is the data your app agrees to provide when your workflow step was executed.
     */
    private List<WorkflowStepOutput> outputs;
    private String outputsAsString;

    /**
     * An optional field that can be used to override app image
     * that is shown in the Workflow Builder.
     */
    private String stepImageUrl;

    /**
     * An optional field that can be used to override the step name
     * that is shown in the Workflow Builder.
     */
    private String stepName;

}
