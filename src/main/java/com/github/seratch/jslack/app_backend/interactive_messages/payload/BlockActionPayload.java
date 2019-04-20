package com.github.seratch.jslack.app_backend.interactive_messages.payload;

import com.github.seratch.jslack.api.model.Message;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 *
 * https://api.slack.com/messaging/interactivity/enabling
 */
@Data
public class BlockActionPayload {

    public static final String TYPE = "block_actions";

    private final String type = TYPE;
    private Team team;
    private User user;
    private String apiAppId;
    private String token;
    private Container container;
    private String triggerId;
    private Channel channel;
    private Message message;
    private String responseUrl;
    private List<Action> actions;

    @Data
    public static class Team {
        private String id;
        private String domain;
    }

    @Data
    public static class User {
        private String id;
        private String username;
        private String name;
        private String teamId;
    }

    @Data
    public static class Container {
        private String type;
        private String messageTs;
        private Integer attachmentId;
        private String channelId;
        private String text;
        @SerializedName("is_ephemeral")
        private boolean ephemeral;
        @SerializedName("is_app_unfurl")
        private boolean app_unfurl;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class Action {
        private String actionId;
        private String blockId;
        private Text text;
        private String value;
        private String type;
        private String actionTs;

        @Data
        public static class Text {
            private String type;
            private String text;
            private boolean emoji;
        }
    }

}
