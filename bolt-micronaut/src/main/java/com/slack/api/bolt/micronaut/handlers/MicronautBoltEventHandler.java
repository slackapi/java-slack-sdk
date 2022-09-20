package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.model.event.Event;

public interface MicronautBoltEventHandler<E extends Event> extends BoltEventHandler<E> {

    Class<E> getEventType();

}
