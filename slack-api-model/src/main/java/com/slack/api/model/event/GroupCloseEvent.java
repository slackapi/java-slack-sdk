package com.slack.api.model.event;

import lombok.Data;

/**
 * The group_close event is sent to all connections for a user when a private channel is closed by that user.
 * <p>
 * This event is not available to bot user subscriptions in the Events API.
 * <p>
 * https://api.slack.com/events/group_close
 */
@Data
public class GroupCloseEvent implements Event {

    public static final String TYPE_NAME = "group_close";

    private final String type = TYPE_NAME;
    private String user;
    private String channel;

}