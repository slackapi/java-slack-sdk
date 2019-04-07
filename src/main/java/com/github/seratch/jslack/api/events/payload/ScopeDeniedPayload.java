package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ScopeDeniedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ScopeDeniedPayload implements EventsApiPayload<ScopeDeniedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ScopeDeniedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
