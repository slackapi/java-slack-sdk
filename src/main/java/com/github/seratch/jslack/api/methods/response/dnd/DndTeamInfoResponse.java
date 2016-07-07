package com.github.seratch.jslack.api.methods.response.dnd;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.Map;

@Data
public class DndTeamInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    // user.id -> info
    private Map<String, DndTeamMemberInfo> users;

    @Data
    public static class DndTeamMemberInfo {
        private boolean dndEnabled;
        private Integer nextDndStartTs;
        private Integer nextDndEndTs;
    }
}