package com.slack.api.methods.response.agents.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Response for agents.conversations.create.
 * <p>
 * NOTE: Slack Code / code channels is in a developer-GA state; only the common top-level fields are modeled here.
 * Additional response fields can be read via {@link #getHttpResponseHeaders()} consumers or a future revision once the
 * response shape is stabilized.
 */
@Data
public class AgentsConversationsCreateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private transient Map<String, List<String>> httpResponseHeaders;
}
