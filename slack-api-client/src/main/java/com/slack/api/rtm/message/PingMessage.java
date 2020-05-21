package com.slack.api.rtm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * https://api.slack.com/rtm#ping_and_pong
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
