package com.slack.api.app_backend.interactive_components.payload;

import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/reference/workflows/workflow_step_edit
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStepEditPayload {

    public static final String TYPE = "workflow_step_edit";

    private final String type = TYPE;
    private String actionTs;
    private Team team;
    private User user;
    private String callbackId;
    private String triggerId;
    private WorkflowStep workflowStep;

    @Data
    public static class Team {
        private String id;
        private String domain;
        private String enterpriseId;
        private String enterpriseName;
    }

    @Data
    public static class User {
        private String id;
        private String username;
        private String teamId;
    }

    @Data
    public static class WorkflowStep {
        private String workflowId;
        private String stepId;
        private Map<String, WorkflowStepInput> inputs;
        private List<WorkflowStepOutput> outputs;
    }

}
