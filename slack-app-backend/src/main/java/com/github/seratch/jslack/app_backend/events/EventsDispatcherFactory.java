package com.github.seratch.jslack.app_backend.events;

/**
 * Creates EventsDispatcher instances.
 */
public class EventsDispatcherFactory {

    private EventsDispatcherFactory() {
    }

    /**
     * Returns an EventsDispatcher.
     */
    public static EventsDispatcher getInstance() {
        return new EventsDispatcherImpl();
    }

}
