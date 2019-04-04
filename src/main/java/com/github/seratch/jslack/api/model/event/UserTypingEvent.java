package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The user_typing event is sent to all members of a channel when a user is typing a message in that channel.
 * <p>
 * https://api.slack.com/events/user_typing
 */
@Data
public class UserTypingEvent implements Event {

    private final String type = "user_typing";
    private String channel;
    private String user;
}