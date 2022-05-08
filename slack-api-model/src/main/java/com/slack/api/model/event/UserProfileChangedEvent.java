package com.slack.api.model.event;

import com.slack.api.model.User;
import lombok.Data;

/**
 * The user_profile_changed event is sent to all connections for a workspace when a user's profile data is changed.
 * The event is identical to the existing user_change event. Both user_change and user_profile_changed are dispatched
 * at the exact same time. Use the users.profile.set method to update user profile data.
 * <p>
 * https://api.slack.com/events/user_profile_changed
 */
@Data
public class UserProfileChangedEvent implements Event {

    public static final String TYPE_NAME = "user_profile_changed";

    private final String type = TYPE_NAME;
    private User user; // TODO: make sure the available attributes
    private Integer cacheTs;
    private String eventTs;
}