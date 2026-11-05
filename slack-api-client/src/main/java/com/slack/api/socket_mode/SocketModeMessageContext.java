package com.slack.api.socket_mode;

import java.io.IOException;

/** Carries queue metadata through the existing, overridable processMessage(String) method. */
final class SocketModeMessageContext {
    private static final ThreadLocal<Long> RECEIVED_AT_NANOS = new ThreadLocal<>();

    private SocketModeMessageContext() {}

    static Long receivedAtNanos() {
        return RECEIVED_AT_NANOS.get();
    }

    static void process(SocketModeClient client, SocketModeMessage message) throws IOException {
        Long previous = RECEIVED_AT_NANOS.get();
        RECEIVED_AT_NANOS.set(message.getReceivedAtNanos());
        try {
            client.processMessage(message.getPayload());
        } finally {
            if (previous == null) {
                RECEIVED_AT_NANOS.remove();
            } else {
                RECEIVED_AT_NANOS.set(previous);
            }
        }
    }
}
