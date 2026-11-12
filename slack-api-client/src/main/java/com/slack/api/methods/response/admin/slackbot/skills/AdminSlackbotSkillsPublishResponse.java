package com.slack.api.methods.response.admin.slackbot.skills;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminSlackbotSkillsPublishResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<FailedSkill> failedSkills;
    private ResponseMetadata responseMetadata;

    @Data
    public static class FailedSkill {
        private String error;
        private String skillId;
    }
}
