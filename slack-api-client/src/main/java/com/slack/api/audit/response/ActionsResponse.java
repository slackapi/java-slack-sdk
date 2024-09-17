package com.slack.api.audit.response;

import com.google.gson.annotations.SerializedName;
import com.slack.api.audit.AuditApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActionsResponse implements AuditApiResponse {
    private transient String rawBody;

    @Deprecated // when an error is returned, it will be an exception instead
    private boolean ok = true;
    @Deprecated // when an error is returned, it will be an exception instead
    private String warning;
    @Deprecated // when an error is returned, it will be an exception instead
    private String error;
    @Deprecated // when an error is returned, it will be an exception instead
    private String needed;
    @Deprecated // when an error is returned, it will be an exception instead
    private String provided;

    private Actions actions;

    @Data
    public static class Actions {
        private List<String> workspaceOrOrg;
        private List<String> user;
        private List<String> file;
        private List<String> channel;
        private List<String> app;
        private List<String> message;
        private List<String> workflowBuilder;
        private List<String> barrier;
        private List<String> huddle;
        private List<String> anomaly;
        @SerializedName("slack_cli")
        private List<String> slackCLI;
        private List<String> subteam;
        private List<String> role;
        private List<String> accountTypeRole;
        private List<String> appApprovalAutomationRule;
        private List<String> workflowV2;
        private List<String> canvas;
        private List<String> function;
        private List<String> salesElevate;
        private List<String> nativeDlp;
        private List<String> template;
    }
}
