package com.github.seratch.jslack.app_backend.events.payload;

import com.slack.api.model.event.Event;

import java.util.List;

/**
 * https://api.slack.com/types/event
 */
public interface EventsApiPayload<E extends Event> {

    String TYPE = "event_callback";

    // token

    String getToken();

    void setToken(String token);

    // teamId

    String getTeamId();

    void setTeamId(String teamId);

    // apiAppId

    String getApiAppId();

    void setApiAppId(String apiAppId);

    // event

    E getEvent();

    void setEvent(E event);

    // type

    String getType();

    void setType(String type);

    // eventId

    String getEventId();

    void setEventId(String eventId);

    // eventTime

    Integer getEventTime();

    void setEventTime(Integer eventTime);

    // authedUsers

    List<String> getAuthedUsers();

    void setAuthedUsers(List<String> authedUsers);

    // authedTeams

    List<String> getAuthedTeams();

    void setAuthedTeams(List<String> authedTeams);

    // enterpriseId

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

}
