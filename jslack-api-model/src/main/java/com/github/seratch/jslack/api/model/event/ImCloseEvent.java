package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * The im_close event is sent to all connections for a user when a direct message channel is closed by that user.
 * <p>
 * https://api.slack.com/events/im_close
 */
@Data
public class ImCloseEvent implements Event {

    public static final String TYPE_NAME = "im_close";

    private final String type = TYPE_NAME;
    private String user;
    private String channel;

}