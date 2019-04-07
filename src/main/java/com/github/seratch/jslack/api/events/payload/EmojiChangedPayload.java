package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.EmojiChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class EmojiChangedPayload implements EventsApiPayload<EmojiChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private EmojiChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
