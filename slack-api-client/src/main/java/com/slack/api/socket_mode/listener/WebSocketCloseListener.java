package com.slack.api.socket_mode.listener;

@FunctionalInterface
public interface WebSocketCloseListener {

    void handle(Integer code, String reason);

}
