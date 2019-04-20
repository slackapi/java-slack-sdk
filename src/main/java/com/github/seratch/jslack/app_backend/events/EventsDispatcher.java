package com.github.seratch.jslack.app_backend.events;

import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;

/**
 * Events API requests dispatcher.
 */
public interface EventsDispatcher {

    /**
     * Registers a new EventHandler.
     */
    void register(EventHandler<? extends EventsApiPayload<?>> handler);

    /**
     * Removes an EventHandler.
     */
    void deregister(EventHandler<? extends EventsApiPayload<?>> handler);

    /**
     * Dispatches requests to appropriate event handlers.
     */
    void dispatch(String json);

    /**
     * Enqueues a JSON payload to the internal queue.
     */
    void enqueue(String json);

    /**
     * Starts the internal thread to handle requests.
     */
    void start();

    /**
     * Stops the internal thread to handle requests.
     */
    void stop();

}
