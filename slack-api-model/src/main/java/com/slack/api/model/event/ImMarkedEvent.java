package com.slack.api.model.event;

import lombok.Data;

/**
 * The im_marked event is sent to all open connections for a user
 * when that user moves the read cursor in a direct message channel by calling the im.mark API method.
 * <p>
 * https://api.slack.com/events/im_marked
 */
@Data
public class ImMarkedEvent implements Event {

    public static final String TYPE_NAME = "im_marked";

    private final String type = TYPE_NAME;
    private String channel;
    private String ts;

}