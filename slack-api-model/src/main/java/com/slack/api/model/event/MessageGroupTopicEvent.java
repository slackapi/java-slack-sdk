package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/message/group_topic
 */
@Data
public class MessageGroupTopicEvent implements Event {

    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "group_topic";

    private final String type = TYPE_NAME;
    private final String subtype = SUBTYPE_NAME;

    private String user;
    private String channel;
    private String channelType; // "group"

    private String text;
    private String topic;

    private String ts;
    private String eventTs;
}
