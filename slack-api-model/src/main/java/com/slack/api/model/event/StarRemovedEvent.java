package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class StarRemovedEvent implements Event {

    public static final String TYPE_NAME = "star_removed";

    private final String type = TYPE_NAME;
    private String user;
    private Item item;
    private String eventTs;

    @Data
    public static class Item {
        private String type;
        private String channel;
        private String createdBy; // user id
        private Integer created;

        private Message message;
        private File file; // TODO: correct definition
        private FileComment comment; // TODO: correct definition
    }

    @Data
    public static class Message {
        private String clientMsgId;
        private String type;
        private String user;
        private String text;
        private List<LayoutBlock> blocks;
        private List<Attachment> attachments;
        private String ts;
        private List<String> pinnedTo;
        private String permalink;
    }

}