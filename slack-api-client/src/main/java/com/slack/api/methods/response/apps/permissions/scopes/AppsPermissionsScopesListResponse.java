package com.slack.api.methods.response.apps.permissions.scopes;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;

@Data
public class AppsPermissionsScopesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

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