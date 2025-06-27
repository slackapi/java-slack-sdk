package com.slack.api.model.event;

import com.slack.api.model.User;
import lombok.Data;

/**
 * The user_status_changed event is sent to all connections for a workspace when a user's status is changed.
 * The event is identical to the existing user_change event. Both user_change and user_status_changed are dispatched
 * at the exact same time. Use the users.profile.set method to update user profile data.
 * <p>
 * https://docs.slack.dev/reference/events/user_change
 */
@Data
public class UserStatusChangedEvent implements Event {

    public static final String TYPE_NAME = "user_status_changed";

    private final String type = TYPE_NAME;
    private User user; // TODO: make sure the available attributes
    private Integer cacheTs;
    private String eventTs;
}