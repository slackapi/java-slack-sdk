package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ImCloseEvent;
import lombok.Data;

import java.util.List;

@Data
public class ImClosePayload implements EventsApiPayload<ImCloseEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ImCloseEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
