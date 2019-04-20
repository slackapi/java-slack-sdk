package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelLeftEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelLeftPayload implements EventsApiPayload<ChannelLeftEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelLeftEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
