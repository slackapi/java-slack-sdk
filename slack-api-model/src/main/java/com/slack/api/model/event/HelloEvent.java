package com.slack.api.model.event;

import lombok.Data;

/**
 * The hello event is sent when a connection is opened to the message server.
 * This allows a client to confirm the connection has been correctly opened.
 * <p>
 * https://docs.slack.dev/reference/events/hello
 */
@Data
public class HelloEvent implements Event {

    public static final String TYPE_NAME = "hello";

    private final String type = TYPE_NAME;

}