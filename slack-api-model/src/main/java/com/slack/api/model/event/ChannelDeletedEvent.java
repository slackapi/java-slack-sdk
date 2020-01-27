package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_deleted event is sent to all connections for a workspace when a channel is deleted.
 * Clients can use this to update their local cache of non-joined channels.
 * <p>
 * https://api.slack.com/events/channel_deleted
 */
@Data
public class ChannelDeletedEvent implements Event {

    public static final String TYPE_NAME = "channel_deleted";

    private final String type = TYPE_NAME;
    private String channel;
}