package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelArchiveEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelArchivePayload implements EventsApiPayload<ChannelArchiveEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelArchiveEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
