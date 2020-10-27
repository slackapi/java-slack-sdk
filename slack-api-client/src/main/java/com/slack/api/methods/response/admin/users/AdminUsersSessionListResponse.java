package com.slack.api.methods.response.admin.users;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsersSessionListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<ActiveSession> activeSessions;
    private ResponseMetadata responseMetadata;

    @Data
    public static class ActiveSession {
        private String userId;
        private String sessionId;
        private String teamId;
        private ActiveSessionCreated created;
    }

    @Data
    public static class ActiveSessionCreated {
        private String deviceHardware;
        private String os;
        private String osVersion;
        private String slackClientVersion;
        private String ip;
    }
}