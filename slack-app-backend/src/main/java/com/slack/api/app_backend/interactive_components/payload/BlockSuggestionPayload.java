package com.slack.api.app_backend.interactive_components.payload;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockSuggestionPayload {

    public static final String TYPE = "block_suggestion";
    private final String type = TYPE;

    private Enterprise enterprise;
    private Team team;
    private User user;
    private Container container;
    private String apiAppId;
    private String token;
    private String actionId;
    private String blockId;
    private String value;
    private Channel channel;
    private View view;
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
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

}
