package com.slack.api.methods.response.dnd;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DndInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private boolean dndEnabled;
    private Integer nextDndStartTs;
    private Integer nextDndEndTs;
    private boolean snoozeEnabled;
    private Integer snoozeEndtime;
    private Integer snoozeRemaining;
}