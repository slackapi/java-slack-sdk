package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.Attachment;
import com.slack.api.model.Reaction;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/message/message_replied
 */
@Data
public class MessageRepliedEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "message_replied";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;
    private String channel;

    private boolean hidden;

    private Message message;

    private String eventTs;
    private String ts;

    @Data
    public static class Message {
        private String clientMsgId;

        private final String type = TYPE_NAME;
        private String subtype;

        private String user;
        private String team;

        private MessageEvent.Edited edited;

        private String text;
        private List<LayoutBlock> blocks;
        private List<Attachment> attachments;

        private String ts;
        private String userTeam;
        private String sourceTeam;

        @SerializedName("is_starred")
        private boolean starred;
        private List<String> pinnedTo;
        private List<Reaction> reactions;
    }

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }

}
