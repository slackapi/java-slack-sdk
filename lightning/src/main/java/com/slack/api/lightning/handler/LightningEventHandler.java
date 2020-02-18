package com.slack.api.lightning.handler;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.lightning.context.builtin.DefaultContext;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.Event;

import java.io.IOException;

/**
 * A handler for Events API.
 * @param <E>
 */
@FunctionalInterface
public interface LightningEventHandler<E extends Event> {

    Response apply(EventsApiPayload<E> event, DefaultContext context) throws IOException, SlackApiException;

}
