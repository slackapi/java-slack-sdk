package com.slack.api.methods.request.dnd;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DndSetSnoozeRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `dnd:write`
     */
    private String token;

    /**
     * Number of minutes, from now, to snooze until.
     */
    private Integer numMinutes;
}