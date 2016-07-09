package com.github.seratch.jslack.api.methods.response.team;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Team;
import lombok.Data;

@Data
public class TeamInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Team team;
}