package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.FileDeletedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileDeletedPayload implements EventsApiPayload<FileDeletedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FileDeletedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
