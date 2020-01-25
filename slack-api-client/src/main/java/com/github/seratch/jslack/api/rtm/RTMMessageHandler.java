package com.github.seratch.jslack.api.rtm;

@FunctionalInterface
public interface RTMMessageHandler {

    void handle(String message);

}
