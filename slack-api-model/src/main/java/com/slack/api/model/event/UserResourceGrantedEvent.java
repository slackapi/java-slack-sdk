package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * We send this event when a user grants your workspace app
 * the permissions you recently requested with apps.permissions.users.request.
 * Now you can work on their behalf!
 * <p>
 * https://docs.slack.dev/changelog/2021-01-workspace-apps-retiring-the-platform-graveyard-in-aug-2021
 */
@Data
public class UserResourceGrantedEvent implements Event {

    public static final String TYPE_NAME = "user_resource_granted";

    private final String type = TYPE_NAME;
    private String user;
    private List<String> scopes;
    private String triggerId;

}