package com.slack.api.model.event;

import lombok.Data;

/**
 * The group_deleted event is sent to all members of a private channel when it is deleted.
 * Clients can use this to update their local list of private channels.
 * <p>
 * https://docs.slack.dev/reference/events/group_deleted
 */
@Data
public class GroupDeletedEvent implements Event {

    public static final String TYPE_NAME = "group_deleted";

    private final String type = TYPE_NAME;
    private String channel;
    private Integer dateDeleted;
    private String actorId;
    private String eventTs;

}