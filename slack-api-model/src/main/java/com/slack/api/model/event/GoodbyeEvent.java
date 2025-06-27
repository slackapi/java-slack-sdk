package com.slack.api.model.event;

import lombok.Data;

/**
 * The server intends to close the connection soon.
 * <p>
 * The goodbye event may be sent by a server that expects it will close the connection after an unspecified amount of time.
 * A well-formed client should reconnect to avoid data loss.
 * <p>
 * Other scenarios where you might encounter the goodbye event are:
 * <ul>
 * <li>reaching the maximum duration of a RTM web socket connection (8 hours)
 * <li>your workspace has been inactive for over two minutes
 * </ul>
 * https://docs.slack.dev/reference/events/goodbye
 */
@Data
public class GoodbyeEvent implements Event {

    public static final String TYPE_NAME = "goodbye";

    private final String type = TYPE_NAME;

}
