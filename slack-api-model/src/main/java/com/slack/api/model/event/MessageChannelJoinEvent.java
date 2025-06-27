package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/message/channel_join
 */
@Data
public class MessageChannelJoinEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_join";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String team;
    private String user;
    private String inviter;
    private String channel;
    private String channelType; // "channel"

    private String text;

    private String ts;
    private String eventTs;
}
