package com.github.seratch.jslack.api.methods.response.team;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.IntegrationLog;
import com.github.seratch.jslack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class TeamIntegrationLogsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<IntegrationLog> logs;
    private Paging paging;
}