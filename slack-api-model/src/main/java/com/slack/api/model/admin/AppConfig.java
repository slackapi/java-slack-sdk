package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class AppConfig {
    private String appId;
    private String workflowAuthStrategy; // "builder_choice"
    private DomainRestrictions domainRestrictions;

    @Data
    public static class DomainRestrictions {
        private List<String> emails;
        private List<String> urls;
    }
}
