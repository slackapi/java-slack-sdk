package com.slack.api.methods.response.team;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.IntegrationLog;
import com.slack.api.model.Paging;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TeamIntegrationLogsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<IntegrationLog> logs;
    private Paging paging;
}