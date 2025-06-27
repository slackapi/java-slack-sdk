package com.slack.api.model.event;

import lombok.Data;

/**
 * The group_archive event is sent to all connections for members of a private channel when that private channel is archived.
 * Clients can use this to update their local list of private channels.
 * <p>
 * https://docs.slack.dev/reference/events/group_archive
 */
@Data
public class GroupArchiveEvent implements Event {

    public static final String TYPE_NAME = "group_archive";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
    private Integer isMoved;
    private String actorId;
    private String eventTs;

}