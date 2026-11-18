package com.slack.api.methods.response.slackbot.skills;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.slackbot.Skill;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SlackbotSkillsUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<McpServer> invalidMcpServers;
    private ResponseMetadata responseMetadata;
    private Skill skill;

    @Data
    public static class McpServer {
        private String serviceType;
        private String appId;
        private String serverId;
    }
}
