package com.slack.api.socket_mode;

import java.io.IOException;

/** Carries queue metadata through the existing, overridable processMessage(String) method. */
final class SocketModeMessageContext {
    private static final ThreadLocal<SocketModeMessage> CURRENT = new ThreadLocal<>();

    private SocketModeMessageContext() {}

    static void process(SocketModeClient client, SocketModeMessage message) throws IOException {
        SocketModeMessage previous = CURRENT.get();
        CURRENT.set(message);
        try {
            client.processMessage(message.getPayload());
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }

    static SocketModeMessage take(String payload) {
        SocketModeMessage message = CURRENT.get();
        // Consume before invoking listeners so a nested direct dispatch cannot inherit this timing.
        CURRENT.remove();
        return message != null && message.getPayload().equals(payload)
                ? message
                : new SocketModeMessage(payload, null);
    }
}
