package com.github.seratch.jslack.app_backend.events.payload;

import com.slack.api.model.event.ChannelCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelCreatedPayload implements EventsApiPayload<ChannelCreatedEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private ChannelCreatedEvent event;
}
