package com.slack.api.app_backend.dialogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The payload which is sent when a user clicked on a dialog which has
 * "type": "select" and "data_source": "external"
 * <p>
 * @see <a href="https://api.slack.com/dialogs">Dialogs</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogSuggestionPayload {

    public static final String TYPE = "dialog_suggestion";

    private final String type = TYPE;
    private String token;
    private String actionTs;
    private Team team;
    private User user;
    private Channel channel;
    private String name;
    private String value;
    private String callbackId;

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
