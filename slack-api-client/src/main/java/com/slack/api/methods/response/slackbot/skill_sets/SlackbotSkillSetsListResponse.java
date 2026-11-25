package com.slack.api.methods.response.slackbot.skill_sets;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.slackbot.SkillSet;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SlackbotSkillSetsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ResponseMetadata responseMetadata;
    private List<SkillSet> skillSets;
}
