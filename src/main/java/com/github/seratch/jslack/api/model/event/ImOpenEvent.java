package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The im_open event is sent to all connections for a user when a direct message channel is opened by that user.
 * <p>
 * https://api.slack.com/events/im_open
 */
@Data
public class ImOpenEvent implements Event {

    public static final String TYPE_NAME = "im_open";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;

}