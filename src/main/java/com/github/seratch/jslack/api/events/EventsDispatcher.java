package com.github.seratch.jslack.api.events;

import com.github.seratch.jslack.api.events.payload.EventsApiPayload;

public interface EventsDispatcher {

    void register(EventHandler<? extends EventsApiPayload<?>> handler);

    void deregister(EventHandler<? extends EventsApiPayload<?>> handler);

    void dispatch(String json);

    void enqueue(String json);

    void start();

    void stop();

}
