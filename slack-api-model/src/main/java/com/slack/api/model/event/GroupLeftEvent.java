package com.slack.api.model.event;

import lombok.Data;

/**
 * The group_left event is sent to all connections for a user when that user leaves a private channel.
 * Clients should respond to this message by closing the private channel
 * - this means that when a private channel is left from client A, it will automatically be closed in client B.
 * <p>
 * The channel value is the string identifier for the private channel.
 * <p>
 * In addition to this message, all existing members of the group will receive a group_leave message event.
 * <p>
 * https://docs.slack.dev/reference/events/group_left
 */
@Data
public class GroupLeftEvent implements Event {

    public static final String TYPE_NAME = "group_left";

    private final String type = TYPE_NAME;
    private String channel;
    private String actorId;
    private String eventTs;

}