package com.slack.api.methods.response.apps.permissions.scopes;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppsPermissionsScopesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Scope> scopes;

    @Data
    public static class Scope {
        private List<String> appHome;
        private List<String> team;
        private List<String> channel;
        private List<String> group;
        private List<String> mpim;
        private List<String> im;
        private List<String> user;
    }

}