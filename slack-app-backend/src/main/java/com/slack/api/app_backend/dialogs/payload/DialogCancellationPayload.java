package com.slack.api.app_backend.dialogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see <a href="https://docs.slack.dev/legacy/legacy-dialogs">Dialogs</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogCancellationPayload {

    public static final String TYPE = "dialog_cancellation";

    private final String type = TYPE;
    private String token;
    private String actionTs;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private Channel channel;
    private String callbackId;
    private String responseUrl;
    private String state;
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
