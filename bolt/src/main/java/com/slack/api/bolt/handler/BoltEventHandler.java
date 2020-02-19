package com.slack.api.bolt.handler;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.DefaultContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.Event;

import java.io.IOException;

/**
 * A handler for Events API.
 * @param <E>
 */
@FunctionalInterface
public interface BoltEventHandler<E extends Event> {

    Response apply(EventsApiPayload<E> event, DefaultContext context) throws IOException, SlackApiException;

}
