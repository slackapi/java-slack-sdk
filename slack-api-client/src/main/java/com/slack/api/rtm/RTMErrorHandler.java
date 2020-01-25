package com.slack.api.rtm;

@FunctionalInterface
public interface RTMErrorHandler {

    void handle(Throwable reason);

}
