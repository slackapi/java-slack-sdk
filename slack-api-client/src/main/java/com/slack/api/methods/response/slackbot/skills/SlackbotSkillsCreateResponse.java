package com.slack.api.methods.response.slackbot.skills;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.slackbot.Skill;
import com.slack.api.model.slackbot.SkillMcpServer;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SlackbotSkillsCreateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<SkillMcpServer> invalidMcpServers;
    private ResponseMetadata responseMetadata;
    private Skill skill;
}
