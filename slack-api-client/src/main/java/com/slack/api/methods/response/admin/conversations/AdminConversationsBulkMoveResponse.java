package com.slack.api.methods.response.admin.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminConversationsBulkMoveResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String bulkActionId;
    private List<NotAdded> notAdded;
    private ErrorResponseMetadata responseMetadata;

    @Data
    public static class NotAdded {
        private String channelId;
        private List<String> errors;
    }
}