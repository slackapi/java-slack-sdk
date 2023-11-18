package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.Event;

import java.util.List;

/**
 * https://api.slack.com/types/event
 */
public interface EventsApiPayload<E extends Event> extends CommonEventProperties, EventProperties<E>, AuthenticationProperties, AuthorizationProperties, EnterpriseProperties {

    String TYPE = "event_callback";

    @Deprecated
    List<String> getAuthedUsers();

    @Deprecated
    void setAuthedUsers(List<String> authedUsers);

    @Deprecated
    List<String> getAuthedTeams();

    @Deprecated
    void setAuthedTeams(List<String> authedTeams);

}
