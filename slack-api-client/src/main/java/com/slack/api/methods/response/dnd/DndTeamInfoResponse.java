package com.slack.api.methods.response.dnd;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DndTeamInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    // user.id -> info
    private Map<String, DndTeamMemberInfo> users;

    @Data
    public static class DndTeamMemberInfo {
        private boolean dndEnabled;
        private Integer nextDndStartTs;
        private Integer nextDndEndTs;
    }
}