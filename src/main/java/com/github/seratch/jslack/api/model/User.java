package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

/**
 * - https://api.slack.com/types/user
 * - https://api.slack.com/changelog/2017-09-the-one-about-usernames
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
    @SerializedName("is_restricted")
    private boolean restricted;
    @SerializedName("is_ultra_restricted")
    private boolean ultraRestricted;
    @SerializedName("is_bot")
    private boolean bot;
    @SerializedName("is_stranger")
    private boolean stranger;
    @SerializedName("is_app_user")
    private boolean appUser;
    private Long updated;
    private boolean has2fa;
    private String twoFactorType;
    private boolean hasFiles;
    private String locale;

    @Data
    public static class Profile {

        private String avatarHash;
        private String statusText;
        private String statusTextCanonical;
        private String statusEmoji;
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
        private String imageOriginal;
        @SerializedName("is_custom_image")
        private boolean customImage;

        private Map<String, Field> fields;

        @Data
        public static class Field {
            private String value;
            private String alt;
            private String label;
        }

        @Deprecated
        private String firstName;
        @Deprecated
        private String lastName;
    }
}
