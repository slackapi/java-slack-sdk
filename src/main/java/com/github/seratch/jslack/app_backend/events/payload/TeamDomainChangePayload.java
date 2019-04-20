package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.TeamDomainChangeEvent;
import lombok.Data;

import java.util.List;

@Data
public class TeamDomainChangePayload implements EventsApiPayload<TeamDomainChangeEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private TeamDomainChangeEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
