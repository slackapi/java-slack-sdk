package com.github.seratch.jslack.app_backend.events.payload;

import com.slack.api.model.event.ChannelSharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ChannelSharedPayload implements EventsApiPayload<ChannelSharedEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private ChannelSharedEvent event;
}
