package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * - https://docs.slack.dev/reference/objects/user-object
 * - https://docs.slack.dev/changelog/2017-09-the-one-about-usernames
 */
@Data
public class User {

    private String id;
    private String teamId;
    private String name;
    private boolean deleted;
    private String color;
    private String realName;
    private String tz;
    private String tzLabel;
    private Integer tzOffset;
    private Profile profile;
    @SerializedName("is_admin")
    private boolean admin;
    @SerializedName("is_owner")
    private boolean owner;
    @SerializedName("is_primary_owner")
    private boolean primaryOwner;
    @SerializedName("is_invited_user")
    private boolean invitedUser;

    /**
     * is_restricted indicates the user is a multichannel guest.
     * see also:
     * https://get.slack.help/hc/en-us/articles/201314026-roles-and-permissions-in-slack
     */
    @SerializedName("is_restricted")
    private boolean restricted;
    /**
     * is_ultra_restricted indicates they are a single channel guest.
     * see also:
     * https://get.slack.help/hc/en-us/articles/201314026-roles-and-permissions-in-slack
     */
    @SerializedName("is_ultra_restricted")
    private boolean ultraRestricted;
    @SerializedName("is_bot")
    private boolean bot;
    @SerializedName("is_connector_bot")
    private boolean connectorBot;
    @SerializedName("is_stranger")
    private boolean stranger;
    @SerializedName("is_app_user")
    private boolean appUser;
    private Long updated;
    @SerializedName("has_2fa")
    private boolean has2fa;
    @SerializedName("is_email_confirmed")
    private boolean emailConfirmed;
    private String presence; // away, etc
    private EnterpriseUser enterpriseUser;
    private String twoFactorType;
    private boolean hasFiles;
    private String locale;
    @SerializedName("is_workflow_bot")
    private boolean workflowBot;
    private String whoCanShareContactCard; // "EVERYONE"
    private List<String> teams;
    private String enterpriseId;
    private String enterpriseName;

    @Data
    public static class Profile {

        private String guestChannels;
        private String guestInvitedBy;
        private Long guestExpirationTs;
        private String avatarHash;
        private String statusText;
        private String statusTextCanonical;
        private String statusEmoji;
        private String statusEmojiUrl;
        private Long statusExpiration;

        private String displayName;
        private String displayNameNormalized;
        private String realName;
        private String realNameNormalized;
        private String botId;

        private String title;
        private String email;
        private String skype;
        private String phone;
        private String team;

        private String apiAppId;
        private boolean alwaysActive;

        private String imageOriginal;

        @SerializedName("image_24")
        private String image24;
        @SerializedName("image_32")
        private String image32;
        @SerializedName("image_48")
        private String image48;
        @SerializedName("image_72")
        private String image72;
        @SerializedName("image_192")
        private String image192;
        @SerializedName("image_512")
        private String image512;
        @SerializedName("image_1024")
        private String image1024;

        @SerializedName("is_custom_image")
        private boolean customImage;

        private String pronouns;
        private List<StatusEmojiDisplayInfo> statusEmojiDisplayInfo;

        @Data
        public static class StatusEmojiDisplayInfo {
            private String emojiName;
            private String displayAlias;
            private String displayUrl;
            private String unicode;
        }

        private Map<String, Field> fields;

        @Data
        public static class Field {
            private String value;
            private String alt;
            private String label;
        }

        private String huddleState; // "default_unset" etc.
        private Long huddleStateExpirationTs;

        private String startDate;

        @Deprecated
        private String firstName;
        @Deprecated
        private String lastName;
    }

    @Data
    public static class EnterpriseUser {
        private String id;
        private String enterpriseId;
        private String enterpriseName;
        @SerializedName("is_primary_owner")
        private boolean primaryOwner;
        @SerializedName("is_admin")
        private boolean admin;
        @SerializedName("is_owner")
        private boolean owner;
        private List<String> teams;
    }

}
