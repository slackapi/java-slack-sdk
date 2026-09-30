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
     * Code channel properties to set. Only provided fields are updated.
     */
    private CodeChannel codeChannel;

    /**
     * Agent resource properties to set. Only provided fields are updated.
     */
    private AgentResource agentResource;

    @Data
    @Builder
    public static class CodeChannel {
        private String host;
        private String repo;
        private String branch;
        private String baseBranch;
        private String commitSha;
        private Integer prNumber;
        private String prUrl;
        private String prTitle;
        private String prStatus;
        private String ciUrl;
        private String ciState;
        private List<String> filePaths;
        private String language;
        private String upstreamUrl;
        private String branchUrl;
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
        private String threadTs;
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
