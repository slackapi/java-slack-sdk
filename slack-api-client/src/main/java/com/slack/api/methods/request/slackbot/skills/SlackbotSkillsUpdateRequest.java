package com.slack.api.methods.request.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.update
 */
@Data
@Builder
public class SlackbotSkillsUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill to change.
     */
    private String skillId;

    /**
     * Human-readable label. Max 255 characters.
     */
    private String displayName;

    /**
     * Markdown that replaces the whole document.
     */
    private String instructions;

    /**
     * When Slackbot should use the skill. Max 400 characters. Send an empty string to clear.
     */
    private String description;

    /**
     * One of the skill categories. Send an empty string to clear.
     * Acceptable values: `communication`, `customer_support`, `data_and_analytics`, `engineering`, `product_management`, `productivity`, `professional_development`, `project_management`, `sales`, `system`, `team_management`, `other`
     */
    private String category;

    /**
     * Max 200 characters. May contain `{skill}`. Send an empty string to clear.
     */
    private String examplePrompt;

    /**
     * Apps the skill needs. Replaces the whole list; send `[]` to clear.
     */
    private List<McpServer> mcpServers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class McpServer {
        private String serviceType;
        private String appId;
        private String serverId;
    }
}
