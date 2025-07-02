package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * When an item is starred, the star_added event is sent to all connected clients for the authenticated user who starred the item.
 * <p>
 * See the stars.list method for details of the structure of the item property.
 * <p>
 * https://docs.slack.dev/reference/events/star_added
 */
@Data
public class StarAddedEvent implements Event {

    public static final String TYPE_NAME = "star_added";

    private final String type = TYPE_NAME;
    private String user;
    private Item item;
    private String eventTs;

    @Data
    public static class Item {
        private String type;
        private String channel;
        private String createdBy; // user id
        private Long dateCreate;

        private Message message;
        private File file; // TODO: correct definition
        private FileComment comment; // TODO: correct definition
    }

    @Data
    public static class Message {
        private String clientMsgId;
        private String type;
        private String appId;
        private String team;
        private String user;
        private String botId;
        private BotProfile botProfile;
        private boolean isStarred;
        private String text;
        private List<LayoutBlock> blocks;
        private List<Attachment> attachments;
        private String ts;
        private List<String> pinnedTo;
        private String permalink;
        private Edited edited;
    }

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }

}