package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.AppHomeOpenedEvent;
import lombok.Data;

@Data
public class AppHomeOpenedPayload implements EventsApiPayload<AppHomeOpenedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private AppHomeOpenedEvent event;
    private String type;
    private String eventId;
    private Integer eventTime;
}
