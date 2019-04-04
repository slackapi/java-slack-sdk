package com.github.seratch.jslack.api.events;

public class EventsDispatcherFactory {

    private EventsDispatcherFactory() {
    }

    public static EventsDispatcher getInstance() {
        return new EventsDispatcherImpl();
    }

}
