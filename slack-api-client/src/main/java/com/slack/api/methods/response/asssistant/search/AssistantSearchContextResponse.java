package com.slack.api.methods.response.asssistant.search;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AssistantSearchContextResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Results results;
    private ResponseMetadata responseMetadata;

    @Data
    public static class Results {
        private List<MessageResult> messages;
        private List<FileResult> files;
        private List<ChannelResult> channels;
        private List<UserResult> users;
    }

    @Data
    public static class MessageResult {
        private String authorName;
        private String authorUserId;
        private String teamId;
        private String channelId;
        private String channelName;
        private String messageTs;
        private String content;
        private Boolean isAuthorBot;
        private String permalink;
        private List<LayoutBlock> blocks;
        private ContextMessages contextMessages;
    }

    @Data
    public static class ContextMessages {
        private List<ContextMessage> before;
        private List<ContextMessage> after;
    }

    @Data
    public static class ContextMessage {
        private String text;
        private String userId;
        private String ts;
        private List<LayoutBlock> blocks;
    }

    @Data
    public static class FileResult {
        private String uploaderUserId;
        private String authorUserId;
        private String authorName;
        private String teamId;
        private String fileId;
        private Integer dateCreated;
        private Integer dateUpdated;
        private String title;
        private String fileType;
        private String permalink;
        private String content;
    }

    @Data
    public static class ChannelResult {
        private String teamId;
        private String creatorUserId;
        private String creatorName;
        private Integer dateCreated;
        private Integer dateUpdated;
        private String name;
        private String topic;
        private String purpose;
        private String permalink;
    }

    @Data
    public static class UserResult {
        private String teamId;
        private String userId;
        private String name;
        private String realName;
        private String permalink;
        private String content;
    }
}
