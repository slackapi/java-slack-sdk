package com.github.seratch.jslack.api.methods.response.pins;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.File;
import com.github.seratch.jslack.api.model.FileComment;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.MessageItem;
import lombok.Data;

import java.util.List;

@Data
public class PinsListResponse implements SlackApiResponse {

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