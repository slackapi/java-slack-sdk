package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.listViews
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
