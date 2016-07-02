package com.github.seratch.jslack.rtm;

@FunctionalInterface
public interface RTMMessageHandler {

    void handle(String message);

}
