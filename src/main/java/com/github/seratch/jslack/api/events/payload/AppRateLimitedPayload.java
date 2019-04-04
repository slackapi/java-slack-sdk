package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.AppRateLimitedEvent;
import lombok.Data;

import java.util.List;

@Data
public class AppRateLimitedPayload implements EventsApiPayload<AppRateLimitedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private AppRateLimitedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
