package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ImOpenEvent;
import lombok.Data;

import java.util.List;

@Data
public class ImOpenPayload implements EventsApiPayload<ImOpenEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ImOpenEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
