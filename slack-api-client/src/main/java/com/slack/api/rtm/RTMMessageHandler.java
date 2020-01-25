package com.slack.api.rtm;

@FunctionalInterface
public interface RTMMessageHandler {

    void handle(String message);

}
