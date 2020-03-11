package com.slack.api.model.event;

import lombok.Data;

/**
 * When a private channel is renamed, the group_rename event is sent to all connections for members of a private channel.
 * Clients can use this to update their local list of private channels.
 * <p>
 * https://api.slack.com/events/group_rename
 */
@Data
public class GroupRenameEvent implements Event {

    public static final String TYPE_NAME = "group_rename";

    private final String type = TYPE_NAME;
    private Channel channel;
    private String eventTs;

    @Data
    public static class Channel {
        private String id;
        private String name;
        private String nameNormalized;
        private boolean isGroup;
        private Integer created;
    }

}