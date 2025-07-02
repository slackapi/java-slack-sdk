package com.slack.api.model.event;

import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/message/bot_message
 */
@Data
public class MessageBotEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "bot_message";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String botId;
    private String username;
    private Message.Icons icons;

    private String channel;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;

    private String threadTs;
    private String ts;

    private String eventTs;
    private String channelType; // app_home, channel, group, im, mpim
}
