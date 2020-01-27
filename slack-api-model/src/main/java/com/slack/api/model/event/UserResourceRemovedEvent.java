package com.slack.api.model.event;

import lombok.Data;

/**
 * We send this event when a user removes an existing grant for your workspace app.
 * <p>
 * https://api.slack.com/events/user_resource_removed
 */
@Data
public class UserResourceRemovedEvent implements Event {

    public static final String TYPE_NAME = "user_resource_removed";

    private final String type = TYPE_NAME;
    private String user;
    private String triggerId;

}