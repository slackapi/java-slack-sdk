package com.slack.api.methods.response.agents.sessions;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AgentsSessionsSetStatusResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String status;
    private String agentStatus;
    private String title;

    private transient Map<String, List<String>> httpResponseHeaders;
}
