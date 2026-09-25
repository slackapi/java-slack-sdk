package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setProperties
 */
@Data
@Builder
public class AgentsConversationsSetPropertiesRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to update.
     */
    private String channelId;

    /**
     * New display title for the agent session.
     */
    private String title;

    /**
     * New status for the agent session.
     */
    private String status;

    /**
     * Code channel properties to set. Only provided fields are updated.
     */
    private CodeChannel codeChannel;

    /**
     * Code channel properties to set, as a JSON-encoded string. Only provided fields are updated.
     */
    private String codeChannelAsString;

    /**
     * Agent resource properties to set. Only provided fields are updated.
     */
    private AgentResource agentResource;

    /**
     * Agent resource properties to set, as a JSON-encoded string. Only provided fields are updated.
     */
    private String agentResourceAsString;

    @Data
    @Builder
    public static class CodeChannel {
        private List<ContextBarItem> contextBarItems;
        private SummaryMessage summaryMessage;
    }

    @Data
    @Builder
    public static class ContextBarItem {
        private String key;
        private String label;
        private String icon;
        private String url;
        private String itemType;
    }

    @Data
    @Builder
    public static class SummaryMessage {
        private String messageTs;
    }

    @Data
    @Builder
    public static class AgentResource {
        private String url;
        private String resourceType;
        private String title;
        private String provider;
    }

}
