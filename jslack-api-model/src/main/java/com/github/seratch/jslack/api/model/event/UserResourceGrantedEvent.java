package com.github.seratch.jslack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * We send this event when a user grants your workspace app
 * the permissions you recently requested with apps.permissions.users.request.
 * Now you can work on their behalf!
 * <p>
 * https://api.slack.com/events/user_resource_granted
 */
@Data
public class UserResourceGrantedEvent implements Event {

    public static final String TYPE_NAME = "user_resource_granted";

    private final String type = TYPE_NAME;
    private String user;
    private List<String> scopes;
    private String triggerId;

}