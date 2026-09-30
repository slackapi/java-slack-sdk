package com.slack.api.methods.response.agents.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AgentsConversationsGetCanvasResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String canvasId;
    private String title;
    private String content;
    private List<Comment> comments;
    private boolean hasMoreComments;

    private transient Map<String, List<String>> httpResponseHeaders;

    @Data
    public static class Comment {
        private String id;
        private String ts;
        private String userId;
        private String text;
        private String quotedText;
        private boolean isResolved;
        private List<Reply> replies;
        private boolean hasMoreReplies;
    }

    @Data
    public static class Reply {
        private String ts;
        private String userId;
        private String text;
    }
}
