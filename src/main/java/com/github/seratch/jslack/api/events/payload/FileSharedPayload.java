package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.FileSharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileSharedPayload implements EventsApiPayload<FileSharedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FileSharedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
