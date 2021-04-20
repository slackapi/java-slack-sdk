package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/message/channel_purpose
 */
@Data
public class MessageChannelPurposeEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "channel_purpose";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String user;
    private String channel;
    private String channelType; // "channel"

    private String text;
    private String purpose;

    private String ts;
    private String eventTs;
}
