package com.slack.api.methods.response.admin.slackbot.skills;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminSlackbotSkillsListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ResponseMetadata responseMetadata;
    private List<Skill> skills;

    @Data
    public static class AutoEnabled {
        private String permissionType;
        private List<String> usergroupIds;
    }

    @Data
    public static class Skill {
        private AutoEnabled autoEnabled;
        private String category;
        private String creatorId;
        private Integer dateCreated;
        private Integer dateUpdated;
        private String description;
        private String displayName;
        private String id;
        private Boolean isOfficial;
        private Integer libraryCount;
        private Integer usageCount;
        private String visibility;
    }
}
