package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * We send this event when a user declines to grant your workspace app
 * the permissions you recently requested with apps.permissions.users.request.
 * <p>
 * https://api.slack.com/events/user_resource_denied
 */
@Data
public class UserResourceDeniedEvent implements Event {

    public static final String TYPE_NAME = "user_resource_denied";

    private final String type = TYPE_NAME;
    private String user;
    private List<String> scopes;
    private String triggerId;

}