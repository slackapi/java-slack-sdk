package com.github.seratch.jslack.api.methods.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class DndEndSnoozeResponse implements SlackApiResponse {

    private boolean ok;
    private String error;

    private boolean dndEnabled;
    private Integer nextDndStartTs;
    private Integer nextDndEndTs;
    private boolean snoozeEnabled;
}