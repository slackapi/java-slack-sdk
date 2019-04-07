package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.MemberLeftChannelEvent;
import lombok.Data;

import java.util.List;

@Data
public class MemberLeftChannelPayload implements EventsApiPayload<MemberLeftChannelEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private MemberLeftChannelEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}
