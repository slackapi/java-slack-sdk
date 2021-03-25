package com.slack.api.methods.response.admin.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsersSessionGetSettingsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<SessionSetting> sessionSettings;
    private List<String> noSettingsApplied; // user IDs

    @Data
    public static class SessionSetting {
        private String userId;
        private Boolean desktopAppBrowserQuit;
        private Integer duration;
    }

    private ErrorResponseMetadata responseMetadata;
}