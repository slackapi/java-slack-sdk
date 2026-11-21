package com.slack.api.socket_mode.listener;

import com.slack.api.socket_mode.SocketModeMessage;

@FunctionalInterface
public interface WebSocketMessageListener {

    void handle(String message);

    /** Receives queue timing when available; existing string listeners continue to work unchanged. */
    default void handleWithMetadata(SocketModeMessage message) {
        handle(message.getPayload());
    }

}
