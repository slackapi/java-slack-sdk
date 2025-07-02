package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/message/channel_leave
 */
@Data
public class MessageChannelLeaveEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_leave";

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
