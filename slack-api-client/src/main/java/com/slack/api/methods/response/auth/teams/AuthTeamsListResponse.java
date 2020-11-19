package com.slack.api.methods.response.auth.teams;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AuthTeamsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Team> teams;
    private ResponseMetadata responseMetadata;

    @Data
    public static class Team {
        private String id;
        private String name;
    }
}