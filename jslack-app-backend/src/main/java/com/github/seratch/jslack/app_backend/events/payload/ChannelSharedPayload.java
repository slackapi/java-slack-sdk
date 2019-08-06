package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelSharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelSharedPayload implements EventsApiPayload<ChannelSharedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelSharedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
