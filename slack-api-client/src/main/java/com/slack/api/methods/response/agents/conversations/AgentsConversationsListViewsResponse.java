package com.slack.api.methods.response.agents.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AgentsConversationsListViewsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<View> views;
    private List<SessionView> sessionViews;

    private transient Map<String, List<String>> httpResponseHeaders;

    @Data
    public static class View {
        private String viewId;
        private String fileId;
        private String viewKey;
        private String label;
        private Integer dateAdded;
        private Integer contentVersion;
    }

    @Data
    public static class SessionView {
        private String viewId;
        private String type;
        private String fileId;
        private String viewKey;
        private String name;
        private String ownerBotUserId;
        private Integer dateAdded;
        private Integer contentVersion;
    }
}
