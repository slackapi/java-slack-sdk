package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_archive event is sent to all connections for a workspace when a channel is archived.
 * Clients can use this to update their local list of channels.
 * <p>
 * https://docs.slack.dev/reference/events/channel_archive
 */
@Data
public class ChannelArchiveEvent implements Event {

    public static final String TYPE_NAME = "channel_archive";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
    private Integer isMoved;
    private String eventTs;
}