package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class AppFunctionPermissions {

    private Distribution distribution;
    private AllowedEntities allowedEntities;
    private AllowedByAdmin allowedByAdmin;

    @Data
    public static class Distribution {
        private String type;
        private List<String> userIds;
    }

    @Data
    public static class AllowedEntities {
        private String type;
        private List<String> userIds;
        private List<String> teamIds;
        private List<String> orgIds;
        private List<String> channelIds;
    }

    @Data
    public static class AllowedByAdmin {
        private String type;
        private List<String> userIds;
    }
}
