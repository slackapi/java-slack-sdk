package com.slack.api.app_backend.interactive_components.payload;

import com.slack.api.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see <a href="https://api.slack.com/actions">Actions</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageActionPayload {

    public static final String TYPE = "message_action";

    private final String type = TYPE;
    private String callbackId;
    private String triggerId;
    private String messageTs;
    private String responseUrl;
    private Message message;
    private Team team;
    private User user;
    private Channel channel;
    private String token;
    private String actionTs;

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
