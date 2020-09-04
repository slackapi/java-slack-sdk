package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminConversationsGetConversationPrefsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ConversationPrefs prefs;
    private ErrorResponseMetadata responseMetadata;

    @Data
    public static class ConversationPref {
        private List<String> type;
        private List<String> user;
    }

    @Data
    public static class ConversationPrefs {
        private ConversationPref whoCanPost;
        private ConversationPref canThread;
    }
}