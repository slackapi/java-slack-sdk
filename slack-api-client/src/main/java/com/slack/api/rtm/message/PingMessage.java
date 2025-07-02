package com.slack.api.rtm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * https://docs.slack.dev/legacy/legacy-rtm-api#ping-pong
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingMessage implements RTMMessage {

    public static final String TYPE_NAME = "ping";

    private Long id;
    private final String type = TYPE_NAME;
    private Instant time;
}
