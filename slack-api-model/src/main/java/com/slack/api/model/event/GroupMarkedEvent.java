package com.slack.api.model.event;

import lombok.Data;

/**
 * The group_marked event is sent to all open connections for a user
 * when that user moves the read cursor in a private channel by calling the groups.mark API method.
 * <p>
 * https://docs.slack.dev/reference/events/group_marked
 */
@Data
public class GroupMarkedEvent implements Event {

    public static final String TYPE_NAME = "group_marked";

    private final String type = TYPE_NAME;
    private String channel;
    private String ts;

}