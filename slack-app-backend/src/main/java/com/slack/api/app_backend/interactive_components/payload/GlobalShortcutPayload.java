package com.slack.api.app_backend.interactive_components.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalShortcutPayload {

    public static final String TYPE = "shortcut";

    private final String type = TYPE;
    private String token;
    private String actionTs;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private String callbackId;
    private String triggerId;
    private boolean isEnterpriseInstall;

    @Data
    public static class Enterprise {
        private String id;
        private String name;
    }

    @Data
    public static class Team {
        private String id;
        private String domain;
        private String enterpriseId;
        private String enterpriseName;
    }

    @Data
    public static class User {
        private String id;
        private String username;
        private String teamId;
    }

}
