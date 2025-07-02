package com.slack.api.model.event;

import lombok.Data;

/**
 * The reconnect_url event is currently unsupported and experimental.
 * <p>
 * https://docs.slack.dev/reference/events/reconnect_url
 */
@Data
public class ReconnectUrlEvent implements Event {

    public static final String TYPE_NAME = "reconnect_url";

    private final String type = TYPE_NAME;

}