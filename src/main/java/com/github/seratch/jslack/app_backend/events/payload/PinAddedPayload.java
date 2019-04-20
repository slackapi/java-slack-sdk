package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.PinAddedEvent;
import lombok.Data;

import java.util.List;

@Data
public class PinAddedPayload implements EventsApiPayload<PinAddedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private PinAddedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
