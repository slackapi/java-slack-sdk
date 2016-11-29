package com.github.seratch.jslack.api.methods.request.dnd;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DndSetSnoozeRequest implements SlackApiRequest {

    private String token;
    private Integer numMinutes;
}