package com.github.seratch.jslack.api.methods.response.team;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.Login;
import com.slack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class TeamAccessLogsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Login> logins;
    private Paging paging;
}