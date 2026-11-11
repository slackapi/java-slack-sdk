package com.slack.api.model.slackbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.create
 * https://docs.slack.dev/reference/methods/slackbot.skills.update
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillMcpServer {
    private String appId;
    private String serverId;
    private String serviceType;
}
