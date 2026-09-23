package com.slack.api.methods.response.agents.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Response for agents.conversations.setCommands.
 * <p>
 * NOTE: Slack Code / code channels is in a developer-GA state; only the common top-level fields are modeled here.
 */
@Data
public class AgentsConversationsSetCommandsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private transient Map<String, List<String>> httpResponseHeaders;
}
