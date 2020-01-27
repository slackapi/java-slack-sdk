package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/message/thread_broadcast
 */
@Data
public class MessageThreadBroadcastEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "thread_broadcast";

    private String clientMsgId;

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;
    private String channel;
    private String user;

    private Message.MessageRoot root;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;

    private String ts;
    private String threadTs;

    private String eventTs;
    private String channelType; // app_home, channel, group, im, mpim

}
