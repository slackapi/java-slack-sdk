package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/message/channel_name
 */
@Data
public class MessageChannelNameEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_name";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String team;
    private String user;
    private String name;
    private String oldName;
    private String channel;
    private String channelType; // "channel"

    private String text;

    private String ts;
    private String eventTs;
}
