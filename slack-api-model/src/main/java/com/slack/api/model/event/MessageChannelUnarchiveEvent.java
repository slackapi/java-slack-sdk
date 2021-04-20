package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/message/channel_unarchive
 */
@Data
public class MessageChannelUnarchiveEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_unarchive";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String team;
    private String user;
    private String channel;
    private String channelType; // "channel"

    private String text;

    private String ts;
    private String eventTs;
}
