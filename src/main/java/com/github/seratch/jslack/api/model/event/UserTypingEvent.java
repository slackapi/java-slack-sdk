package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The user_typing event is sent to all members of a channel when a user is typing a message in that channel.
 * <p>
 * https://api.slack.com/events/user_typing
 */
@Data
public class UserTypingEvent implements Event {

    public static final String TYPE_NAME = "user_typing";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
}