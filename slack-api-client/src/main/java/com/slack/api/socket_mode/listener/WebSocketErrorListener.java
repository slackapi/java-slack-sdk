package com.slack.api.socket_mode.listener;

@FunctionalInterface
public interface WebSocketErrorListener {

    void handle(Throwable reason);

}
