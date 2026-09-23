package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.listViews
 * <p>
 * List the views currently attached to a code channel. Requires the bot scope {@code code_channels:manage}.
 * <p>
 * NOTE: This method is part of the Slack Code / code channels feature, which is in a developer-GA state.
 * The request/response shapes may change before general availability.
 */
@Data
@Builder
public class AgentsConversationsListViewsRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to list views for.
     */
    private String channelId;

}
