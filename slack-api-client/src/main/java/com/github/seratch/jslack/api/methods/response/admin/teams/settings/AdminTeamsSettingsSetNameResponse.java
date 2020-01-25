package com.github.seratch.jslack.api.methods.response.admin.teams.settings;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class AdminTeamsSettingsSetNameResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
}