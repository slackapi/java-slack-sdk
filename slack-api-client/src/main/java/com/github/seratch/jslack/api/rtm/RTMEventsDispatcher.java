package com.github.seratch.jslack.api.rtm;

import com.slack.api.model.event.Event;

/**
 * Real Time Messaging API requests dispatcher.
 */
public interface RTMEventsDispatcher {

    /**
     * Registers a new EventHandler.
     */
    void register(RTMEventHandler<? extends Event> handler);

    /**
     * Removes a EventHandler.
     */
    void deregister(RTMEventHandler<? extends Event> handler);

    /**
     * Dispatches requests to appropriate event handlers.
     */
    void dispatch(String json);

    /**
     * Converts this dispatcher to {@link RTMMessageHandler}.
     */
    RTMMessageHandler toMessageHandler();

}
