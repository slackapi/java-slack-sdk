package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class AppWorkflowPermissions {

    private Boolean complete;
    private WhoCanRun whoCanRun;

    @Data
    public static class WhoCanRun {
        private String permissionType; // everyone | app_collaborators | named_entities
        private List<String> userIds;
        private List<String> channelIds;
        private List<String> teamIds;
        private List<String> orgIds;
    }
}
