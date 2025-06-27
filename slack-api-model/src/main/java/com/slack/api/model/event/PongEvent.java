package com.slack.api.model.event;

import lombok.Data;

import java.time.Instant;

/**
 * The pong event is sent in response to a 'ping' message previously sent. The id and
 * other fields will match that of the ping message.
 * <p>
 * https://docs.slack.dev/legacy/legacy-rtm-api#ping-pong
 */
@Data
public class PongEvent implements Event {
    public static final String TYPE_NAME = "pong";
    private final String type = TYPE_NAME;

    private Long replyTo;
    private Instant time;
}