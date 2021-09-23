package com.slack.api.methods.response.team.profile;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Team;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TeamProfileGetResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Profiles profile;

    @Data
    public static class Profiles {
        private List<Team.Profile> fields;
    }
}