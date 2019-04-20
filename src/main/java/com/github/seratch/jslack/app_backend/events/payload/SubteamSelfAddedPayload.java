package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.SubteamSelfAddedEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubteamSelfAddedPayload implements EventsApiPayload<SubteamSelfAddedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private SubteamSelfAddedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
