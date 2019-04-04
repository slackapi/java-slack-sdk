package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ChannelHistoryChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelHistoryChangedPayload implements EventsApiPayload<ChannelHistoryChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ChannelHistoryChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
