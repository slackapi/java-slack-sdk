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

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
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
    }
}
