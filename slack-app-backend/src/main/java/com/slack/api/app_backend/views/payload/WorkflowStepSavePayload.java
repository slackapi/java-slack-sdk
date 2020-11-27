package com.slack.api.app_backend.views.payload;

import com.slack.api.model.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @see <a href="https://api.slack.com/block-kit/surfaces/modals">Modals</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStepSavePayload {
    public static final String TYPE = "view_submission";
    private final String type = TYPE;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private String apiAppId;
    private String token;
    private String triggerId;
    private View view;
    private List<ResponseUrl> responseUrls;
    private WorkflowStep workflowStep;
    private boolean isEnterpriseInstall;

    @Data
    public static class Enterprise {
        private String id;
        private String name;
    }

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
        private String name;
        private String teamId;
    }

    @Data
    public static class ResponseUrl {
        private String blockId;
        private String actionId;
        private String channelId;
        private String responseUrl;
    }

    @Data
    public static class WorkflowStep {
        private String workflowStepEditId;
        private String workflowId;
        private String stepId;
    }
}
