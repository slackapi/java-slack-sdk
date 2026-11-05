package com.slack.api.methods.response.agents.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AgentsConversationsSetViewResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String channelId;
    private String viewId;
    private String fileId;
    private Integer contentVersion;
    private String type;
    private String canvasId;

    private transient Map<String, List<String>> httpResponseHeaders;
}
