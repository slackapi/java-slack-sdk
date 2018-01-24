package com.github.seratch.jslack.api.rtm;

@FunctionalInterface
public interface RTMErrorHandler {

    void handle(Throwable reason);

}
