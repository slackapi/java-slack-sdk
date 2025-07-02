package com.slack.api.methods.response.apps.event.authorizations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/apps.event.authorizations.list
 * https://docs.slack.dev/changelog/2020-09-15-events-api-truncate-authed-users
 */
@Data
public class AppsEventAuthorizationsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Authorization> authorizations;
    private String cursorNext;

    @Data
    public static class Authorization {
        private String enterpriseId;
        private String teamId;
        private String userId;
        private Boolean isBot;
        private Boolean isEnterpriseInstall;
    }
}