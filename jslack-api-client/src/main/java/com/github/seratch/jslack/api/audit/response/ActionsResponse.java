package com.github.seratch.jslack.api.audit.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ActionsResponse implements SlackApiResponse {
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
    }
}
