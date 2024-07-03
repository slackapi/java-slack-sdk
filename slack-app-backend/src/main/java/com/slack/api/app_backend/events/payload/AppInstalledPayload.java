package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.AppInstalledEvent;
import lombok.Data;

import java.util.List;

@Data
public class AppInstalledPayload implements EventsApiPayload<AppInstalledEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private List<Authorization> authorizations;
    private boolean isExtSharedChannel;
    private String eventId;
    private Integer eventTime;
    private String eventContext;

    private AppInstalledEvent event;
}
