package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The channel_marked event is sent to all open connections for a user
 * when that user moves the read cursor in a channel by calling the channels.mark API method.
 * <p>
 * https://api.slack.com/events/channel_marked
 */
@Data
public class ChannelMarkedEvent implements Event {

    public static final String TYPE_NAME = "channel_marked";

    private final String type = TYPE_NAME;
    private String channel;
    private String ts;

}