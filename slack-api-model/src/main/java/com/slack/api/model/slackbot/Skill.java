package com.slack.api.model.slackbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/ai/slackbot-skills
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private String access;
    private String category;
    private String creatorId;
    private Integer dateCreated;
    private Integer dateUpdated;
    private String description;
    private String displayName;
    private String examplePrompt;
    private String id;
    private String instructions;
    private Boolean isOfficial;
    private Integer libraryCount;
    private List<McpServer> mcpServers;
    private String name;
    private String permalink;
    private String type;
    private Integer usageCount;
    private String visibility;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class McpServer {
        private String appId;
        private String displayName;
        private Boolean isAvailable;
        private String serverId;
        private String serviceType;
    }
}
