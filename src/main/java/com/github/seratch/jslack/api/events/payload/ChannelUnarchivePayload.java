package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelUnarchiveEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelUnarchivePayload implements EventsApiPayload<ChannelUnarchiveEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelUnarchiveEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
