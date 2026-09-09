package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The user clicked the native stop button while the agent session was processing.
 * <p>
 * https://docs.slack.dev/reference/events/agent_session_stopped
 */
@Data
public class AgentSessionStoppedEvent implements Event {

    public static final String TYPE_NAME = "agent_session_stopped";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
    private String threadTs;
    private List<String> streamingMessageTs;
    private String eventTs;
}
