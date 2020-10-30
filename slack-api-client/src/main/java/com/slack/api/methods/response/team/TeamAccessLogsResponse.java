package com.slack.api.methods.response.team;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Login;
import com.slack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class TeamAccessLogsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Login> logins;
    private Paging paging;
}