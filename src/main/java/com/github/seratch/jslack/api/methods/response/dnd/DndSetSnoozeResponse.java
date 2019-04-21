package com.github.seratch.jslack.api.methods.response.dnd;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class DndSetSnoozeResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean snoozeEnabled;
    private Integer snoozeEndtime;
    private Integer snoozeRemaining;
}