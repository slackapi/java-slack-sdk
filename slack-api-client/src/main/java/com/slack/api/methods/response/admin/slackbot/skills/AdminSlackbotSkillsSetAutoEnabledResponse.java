package com.slack.api.methods.response.admin.slackbot.skills;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminSlackbotSkillsSetAutoEnabledResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<String> invalidUsergroupIds;
    private String permissionType;
    private ResponseMetadata responseMetadata;
    private String skillId;
    private List<String> usergroupIds;
}
