package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.TeamRenameEvent;
import lombok.Data;

import java.util.List;

@Data
public class TeamRenamePayload implements EventsApiPayload<TeamRenameEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private TeamRenameEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
