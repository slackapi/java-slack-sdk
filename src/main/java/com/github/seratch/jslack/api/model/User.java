package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * https://api.slack.com/types/user
 */
@Data
public class User {

    private String id;
    private String name;
    private boolean deleted;
    private String color;
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
    private boolean has2fa;
    private String twoFactorType;
    private boolean hasFiles;

    @Data
    public static class Profile {
        private String firstName;
        private String lastName;
        private String realName;
        private String email;
        private String skype;
        private String phone;
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
    }
}
