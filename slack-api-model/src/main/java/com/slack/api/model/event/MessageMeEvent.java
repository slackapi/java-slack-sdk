package com.slack.api.model.event;

import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/message/me_message
 */
@Data
public class MessageMeEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "me_message";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;
    private String channel;

    private String username;
    private String user;
    private String botId;

    private String text;
    private List<LayoutBlock> blocks; // rich_text blocks

    private String eventTs;
    private String ts;
    private String channelType; // app_home, channel, group, im, mpim
}
