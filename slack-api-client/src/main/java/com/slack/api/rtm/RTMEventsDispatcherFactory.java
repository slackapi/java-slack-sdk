package com.slack.api.rtm;

/**
 * Creates RTMEventsDispatcher instances.
 */
public class RTMEventsDispatcherFactory {

    private RTMEventsDispatcherFactory() {
    }

    /**
     * Returns an RTMEventsDispatcher.
     */
    public static RTMEventsDispatcher getInstance() {
        return new RTMEventsDispatcherImpl();
    }

}
