package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminConversationsGetConversationPrefsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ConversationPrefs prefs;
    private ErrorResponseMetadata responseMetadata;

    @Data
    public static class ConversationPref {
        private List<String> type; // admin,owner
        private List<String> user; // U1234,U5678
    }

    @Data
    public static class MembershipLimit {
        private Integer value;
    }
    @Data
    public static class CanHuddle {
        private Boolean enabled;
    }
    @Data
    public static class EnableAtChannel {
        private Boolean enabled;
    }
    @Data
    public static class EnableAtHere {
        private Boolean enabled;
    }

    @Data
    public static class ConversationPrefs {
        private ConversationPref whoCanPost;
        private ConversationPref canThread;
        private MembershipLimit membershipLimit;
        private CanHuddle canHuddle;
        private EnableAtChannel enableAtChannel;
        private EnableAtHere enableAtHere;
    }
}