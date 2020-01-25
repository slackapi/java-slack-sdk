package com.github.seratch.jslack.app_backend.events.payload;

import com.slack.api.model.event.ReactionAddedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ReactionAddedPayload implements EventsApiPayload<ReactionAddedEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private ReactionAddedEvent event;
}
