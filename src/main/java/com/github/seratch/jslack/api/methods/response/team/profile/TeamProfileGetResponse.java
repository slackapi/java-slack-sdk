package com.github.seratch.jslack.api.methods.response.team.profile;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Team;
import lombok.Data;

import java.util.List;

@Data
public class TeamProfileGetResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Profiles profile;

    @Data
    public static class Profiles {
        private List<Team.Profile> fields;
    }
}