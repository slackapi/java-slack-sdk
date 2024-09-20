package com.slack.api.bolt.handler;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.Event;

import java.io.IOException;

/**
 * A handler for Assistant Events API.
 *
 * @param <E>
 */
@FunctionalInterface
public interface AssistantEventHandler<E extends Event> {

    void apply(EventsApiPayload<E> event, EventContext context);

}
