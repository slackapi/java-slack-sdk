package com.github.seratch.jslack.api.methods.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class DndSetSnoozeResponse implements SlackApiResponse {

    private boolean ok;
    private String error;

    private boolean snoozeEnabled;
    private Integer snoozeEndtime;
    private Integer snoozeRemaining;
}