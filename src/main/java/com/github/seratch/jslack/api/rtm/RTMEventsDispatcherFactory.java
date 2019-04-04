package com.github.seratch.jslack.api.rtm;

public class RTMEventsDispatcherFactory {

    private RTMEventsDispatcherFactory() {
    }

    public static RTMEventsDispatcher getInstance() {
        return new RTMEventsDispatcherImpl();
    }

}
