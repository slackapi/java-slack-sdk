package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelCreatedPayload implements EventsApiPayload<ChannelCreatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelCreatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
