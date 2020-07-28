package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_rename event is sent to all connections for a workspace when a channel is renamed.
 * Clients can use this to update their local list of channels.
 * <p>
 * https://api.slack.com/events/channel_rename
 */
@Data
public class ChannelRenameEvent implements Event {

    public static final String TYPE_NAME = "channel_rename";

    private final String type = TYPE_NAME;
    private Channel channel;
    private String eventTs;

    @Data
    public static class Channel {
        private String id;
        private String name; // new name
        private String nameNormalized;
        private boolean isChannel;
        private boolean isMpim;
        private Integer created;
    }
}