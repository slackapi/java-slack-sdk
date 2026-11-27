package com.slack.api.socket_mode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** A received message and, when known, its local monotonic receive time. */
@Getter
@RequiredArgsConstructor
public class SocketModeMessage {
    private final String payload;

    /**
     * System.nanoTime() when the message entered the client's queue, or null for
     * custom queues and direct dispatch without timing information. This is not a wall-clock timestamp.
     */
    private final Long receivedAtNanos;
}
