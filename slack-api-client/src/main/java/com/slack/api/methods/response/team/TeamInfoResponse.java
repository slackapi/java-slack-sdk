package com.slack.api.methods.response.team;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Team;
import lombok.Data;

@Data
public class TeamInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Team team;
}