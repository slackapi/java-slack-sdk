package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/message/bot_message
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

    private String ts;

    private String eventTs;
    private String channelType; // app_home, channel, group, im, mpim
}
