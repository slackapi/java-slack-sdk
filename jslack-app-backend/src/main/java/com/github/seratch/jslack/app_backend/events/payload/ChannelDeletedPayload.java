package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelDeletedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelDeletedPayload implements EventsApiPayload<ChannelDeletedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelDeletedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
