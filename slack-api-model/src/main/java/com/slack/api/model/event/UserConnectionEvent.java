package com.slack.api.model.event;

import lombok.Data;

/**
 * Slack sends the user_connection event when a user clicks Connect or Manage on an app with user connections.
 * The subtype is either connect or disconnect. For connect, open the connection modal with the
 * apps.user.connection.requestAuth API method and the trigger_id.
 * <p>
 * https://docs.slack.dev/reference/events/user_connection
 */
@Data
public class UserConnectionEvent implements Event {

    public static final String TYPE_NAME = "user_connection";

    private final String type = TYPE_NAME;
    private String subtype;
    private String user;
    private String triggerId;
    private String enterpriseId;
    private String eventTs;
}
