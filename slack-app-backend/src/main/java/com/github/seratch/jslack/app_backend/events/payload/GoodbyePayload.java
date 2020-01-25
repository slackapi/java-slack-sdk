package com.github.seratch.jslack.app_backend.events.payload;

import com.slack.api.model.event.GoodbyeEvent;
import lombok.Data;

import java.util.List;

@Data
public class GoodbyePayload implements EventsApiPayload<GoodbyeEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private GoodbyeEvent event;
}
