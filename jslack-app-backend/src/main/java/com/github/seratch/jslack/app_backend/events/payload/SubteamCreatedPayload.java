package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.SubteamCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubteamCreatedPayload implements EventsApiPayload<SubteamCreatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private SubteamCreatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
