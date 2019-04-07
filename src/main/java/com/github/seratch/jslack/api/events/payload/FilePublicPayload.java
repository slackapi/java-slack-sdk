package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.FilePublicEvent;
import lombok.Data;

import java.util.List;

@Data
public class FilePublicPayload implements EventsApiPayload<FilePublicEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FilePublicEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
