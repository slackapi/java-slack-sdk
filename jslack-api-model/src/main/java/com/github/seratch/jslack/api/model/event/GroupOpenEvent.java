package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The group_open event is sent to all connections for a user when a group Direct RTMMessage (or mpim) is opened by that user.
 * <p>
 * This event is not available to bot user subscriptions in the Events API.
 * <p>
 * https://api.slack.com/events/group_open
 */
@Data
public class GroupOpenEvent implements Event {

    public static final String TYPE_NAME = "group_open";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;

}