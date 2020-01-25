package com.github.seratch.jslack.lightning.handler;

import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.Event;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.github.seratch.jslack.lightning.context.builtin.DefaultContext;
import com.github.seratch.jslack.lightning.response.Response;

import java.io.IOException;

@FunctionalInterface
public interface LightningEventHandler<E extends Event> {

    Response apply(EventsApiPayload<E> event, DefaultContext context) throws IOException, SlackApiException;

}
