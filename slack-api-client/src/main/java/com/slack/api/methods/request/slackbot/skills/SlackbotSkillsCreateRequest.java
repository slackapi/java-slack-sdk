package com.slack.api.methods.request.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.slackbot.SkillMcpServer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.create
 */
@Data
@Builder
public class SlackbotSkillsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Canvas the caller owns to turn into a skill. Send exactly one of `instructions` or `canvas_id`.
     */
    private String canvasId;

    /**
     * One of the skill categories.
     * Acceptable values: `communication`, `customer_support`, `data_and_analytics`, `engineering`, `product_management`, `productivity`, `professional_development`, `project_management`, `sales`, `system`, `team_management`, `other`
     */
    private String category;

    /**
     * When Slackbot should use the skill. Max 400 characters.
     */
    private String description;

    /**
     * Human-readable label. Max 255 characters; not unique.
     */
    private String displayName;

    /**
     * Max 200 characters. May contain `{skill}`, which becomes the skill mention.
     */
    private String examplePrompt;

    /**
     * Markdown body of the skill. Send exactly one of `instructions` or `canvas_id`.
     */
    private String instructions;

    /**
     * Apps the skill needs, capped at the caller's MCP server limit. Declaring an app doesn't grant access to it.
     */
    private List<SkillMcpServer> mcpServers;

}
