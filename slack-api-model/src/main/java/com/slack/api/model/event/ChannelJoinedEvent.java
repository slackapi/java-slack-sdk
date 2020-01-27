package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_joined event is sent to all connections for a user when that user joins a channel.
 * <p>
 * In addition to this message, all existing members of the channel may receive a channel_join message event.
 * <p>
 * Of course, there's also the fresher, more dependable member_joined_channel. This changelog entry clears it all up.
 * <p>
 * https://api.slack.com/events/channel_joined
 */
@Data
public class ChannelJoinedEvent implements Event {

    public static final String TYPE_NAME = "channel_joined";

    private final String type = TYPE_NAME;
    private Channel channel;

    // TODO: the existence of these attributes has not been verified yet
    @Data
    public static class Channel {
        private String id;
        private String name;
        private Integer created;
        private String creator;
    }
}