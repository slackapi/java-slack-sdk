package com.slack.api.methods.response.admin.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminUsersSessionListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<ActiveSession> activeSessions;
    private ResponseMetadata responseMetadata;

    @Data
    public static class ActiveSession {
        private String userId;
        private String sessionId;
        private String teamId;
        private ActiveSessionCreated created;
        private ActiveSessionRecent recent;
    }

    @Data
    public static class ActiveSessionCreated {
        private String deviceHardware;
        private String os;
        private String osVersion;
        private String slackClientVersion;
        private String ip;
    }

    @Data
    public static class ActiveSessionRecent {
        private String deviceHardware;
        private String os;
        private String osVersion;
        private String slackClientVersion;
        private String ip;
    }
}