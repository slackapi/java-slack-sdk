package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.FileSharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileSharedPayload implements EventsApiPayload<FileSharedEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String contextTeamId;
    private String contextEnterpriseId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private List<Authorization> authorizations;
    private boolean isExtSharedChannel;
    private String eventId;
    private Integer eventTime;
    private String eventContext;

    private FileSharedEvent event;
}
