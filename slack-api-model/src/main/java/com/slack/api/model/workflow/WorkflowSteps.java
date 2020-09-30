package com.slack.api.model.workflow;

import com.slack.api.model.ModelConfigurator;

import java.util.Arrays;
import java.util.List;

public class WorkflowSteps {

    private WorkflowSteps() {
    }

    public static WorkflowStepInput stepInput(ModelConfigurator<WorkflowStepInput.WorkflowStepInputBuilder> configurator) {
        return configurator.configure(WorkflowStepInput.builder()).build();
    }

    public static List<WorkflowStepOutput> asOutputs(WorkflowStepOutput... outputs) {
        return Arrays.asList(outputs);
    }

    public static List<WorkflowStepOutput> asStepOutputs(WorkflowStepOutput... outputs) {
        return Arrays.asList(outputs);
    }

    public static WorkflowStepOutput output(ModelConfigurator<WorkflowStepOutput.WorkflowStepOutputBuilder> configurator) {
        return stepOutput(configurator);
    }

    public static WorkflowStepOutput stepOutput(ModelConfigurator<WorkflowStepOutput.WorkflowStepOutputBuilder> configurator) {
        return configurator.configure(WorkflowStepOutput.builder()).build();
    }

}
