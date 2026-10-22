package com.slack.api.model.event;

import lombok.Data;

/**
 * Slack sends the user_connection_status_requested event when it has no connection status for a user.
 * Answer it with the apps.user.connection.update API method.
 * <p>
 * https://docs.slack.dev/reference/events/user_connection_status_requested
 */
@Data
public class UserConnectionStatusRequestedEvent implements Event {

    public static final String TYPE_NAME = "user_connection_status_requested";

    private final String type = TYPE_NAME;
    private String user;
    private String eventTs;
}
