package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_created event is sent to all connections for a workspace when a new channel is created.
 * Clients can use this to update their local cache of non-joined channels.
 * <p>
 * https://api.slack.com/events/channel_created
 */
@Data
public class ChannelCreatedEvent implements Event {

    public static final String TYPE_NAME = "channel_created";

    private final String type = TYPE_NAME;
    private Channel channel;
    private String eventTs;

    @Data
    public static class Channel {
        private String id;
        private String name;
        private String nameNormalized;
        private boolean isChannel;
        private boolean isShared;
        private boolean isOrgShared;
        private Integer created;
        private String creator;
    }
}