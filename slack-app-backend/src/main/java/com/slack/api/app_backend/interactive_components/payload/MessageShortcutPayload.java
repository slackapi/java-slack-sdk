package com.slack.api.app_backend.interactive_components.payload;

import com.slack.api.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageShortcutPayload {

    public static final String TYPE = "message_action";

    private final String type = TYPE;
    private String callbackId;
    private String triggerId;
    private String messageTs;
    private String responseUrl;
    private Message message;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private Channel channel;
    private String token;
    private String actionTs;
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
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String teamId;
    }

}
