package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.Event;

/**
 * https://api.slack.com/types/event
 */
public interface EventsApiPayload<E extends Event> {

    String getToken();

    void setToken(String token);

    String getTeamId();

    void setTeamId(String teamId);

    String getApiAppId();

    void setApiAppId(String apiAppId);

    E getEvent();

    void setEvent(E event);

    String getType();

    void setType(String type);

    String getEventId();

    void setEventId(String eventId);

    Integer getEventTime();

    void setEventTime(Integer eventTime);

}
