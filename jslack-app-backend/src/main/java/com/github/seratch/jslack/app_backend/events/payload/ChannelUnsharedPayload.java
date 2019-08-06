package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelUnsharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelUnsharedPayload implements EventsApiPayload<ChannelUnsharedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelUnsharedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
