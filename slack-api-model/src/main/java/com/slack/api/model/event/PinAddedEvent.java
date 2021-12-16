package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.File;
import com.slack.api.model.FileComment;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * When an item is pinned in a channel, the pin_added event is sent to all members of that channel.
 * <p>
 * https://api.slack.com/events/pin_added
 */
@Data
public class PinAddedEvent implements Event {

    public static final String TYPE_NAME = "pin_added";

    private final String type = TYPE_NAME;
    private String user;
    private String channelId;
    private Item item;
    private String itemUser;
    private Integer pinCount;
    private PinnedInfo pinnedInfo;
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
        private String appId;
        private String team;
        private String user;
        private String botId;
        private BotProfile botProfile;
        private String text;
        private List<LayoutBlock> blocks;
        private List<Attachment> attachments;
        private String ts;
        private List<String> pinnedTo;
        private String permalink;
    }

    @Data
    public static class PinnedInfo {
        private String channel;
        private String pinnedBy;
        private Long pinnedTs;
    }

}