package com.slack.api.socket_mode.listener;

@FunctionalInterface
public interface WebSocketMessageListener {

    void handle(String message);

}
