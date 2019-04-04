package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.AppUninstalledEvent;
import lombok.Data;

@Data
public class AppUninstalledPayload implements EventsApiPayload<AppUninstalledEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private AppUninstalledEvent event;
    private String type;
    private String eventId;
    private Integer eventTime;

}
