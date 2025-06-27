package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/message/channel_convert_to_public
 */
@Data
public class MessageChannelConvertToPublicEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_convert_to_public";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String user;
    private String text;
    private String channel;
    private String ts;
    private String eventTs;
    private String channelType; // "channel"
}
