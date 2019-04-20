package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.SubteamUpdatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubteamUpdatedPayload implements EventsApiPayload<SubteamUpdatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private SubteamUpdatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
