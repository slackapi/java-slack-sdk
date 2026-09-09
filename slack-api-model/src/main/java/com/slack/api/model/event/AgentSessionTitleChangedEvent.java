package com.slack.api.model.event;

import lombok.Data;

/**
 * The user renamed an agent session.
 * <p>
 * https://docs.slack.dev/reference/events/agent_session_title_changed
 */
@Data
public class AgentSessionTitleChangedEvent implements Event {

    public static final String TYPE_NAME = "agent_session_title_changed";

    private final String type = TYPE_NAME;
    private String channel;
    private String user;
    private String teamId;
    private String threadTs;
    private String title;
    private String previousTitle;
    private String eventTs;
}
