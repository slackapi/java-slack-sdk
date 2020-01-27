package com.slack.api.model.event;

import lombok.Data;

/**
 * The im_created event is sent to all connections for a user when a new direct message channel is created that they are a member of.
 * <p>
 * This message lets the client know that a channel has been created,
 * but the client should show no changes based on this, just update its internal list of IM channels.
 * Usually this event is followed by an im_open event.
 * <p>
 * https://api.slack.com/events/im_created
 */
@Data
public class ImCreatedEvent implements Event {

    public static final String TYPE_NAME = "im_created";

    private final String type = TYPE_NAME;
    private String user;
    private Channel channel;

    @Data
    public static class Channel {
        // TODO
    }

}