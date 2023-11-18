package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.Event;

public interface EventProperties<E extends Event> {
    E getEvent();

    void setEvent(E event);
}
