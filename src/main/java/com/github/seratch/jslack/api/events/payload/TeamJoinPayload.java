package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.TeamJoinEvent;
import lombok.Data;

import java.util.List;

@Data
public class TeamJoinPayload implements EventsApiPayload<TeamJoinEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private TeamJoinEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
