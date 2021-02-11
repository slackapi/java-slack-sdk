package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/channel_id_changed
 */
@Data
public class ChannelIdChangedEvent implements Event {

    public static final String TYPE_NAME = "channel_id_changed";

    private final String type = TYPE_NAME;
    private String oldChannelId;
    private String newChannelId;
    private String eventTs;

}