package com.slack.api.methods.response.pins;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class PinsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<MessageItem> items;

    @Data
    public static class MessageItem {

        private String type;
        private String channel;
        private Message message;
        private File file;
        private FileComment comment;
        private String createdBy;
        private Integer created;
    }

}