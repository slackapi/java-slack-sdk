package com.github.seratch.jslack.api.model.admin;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class AppRequest {
    private String id;
    private App app;
    private User user;
    private Team team;
    private List<Scope> scopes;
    private PreviousResolution previousResolution;
    private String message;
    private Integer dateCreated;

    @Data
    public static class App {
        private String id;
        private String name;
        private String description;
        private String helpUrl;
        private String privacyPolicyUrl;
        private String appHomepageUrl;
        private String appDirectoryUrl;
        @SerializedName("is_app_directory_approved")
        private boolean isAppDirectoryApproved;
        @SerializedName("is_internal")
        private boolean isInternal;
        private String additionalInfo;
        private Icons icons;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String email;
    }

    @Data
    public static class Team {
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class Scope {
        private String name;
        private String description;
        @SerializedName("is_sensitive")
        private boolean isSensitive;
        private String tokenType;
    }

    @Data
    public static class PreviousResolution {
        private String status;
        private List<Scope> scopes;
    }

    @Data
    public static class Icons {
        @SerializedName("image_32")
        private String image32;
        @SerializedName("image_36")
        private String image36;
        @SerializedName("image_48")
        private String image48;
        @SerializedName("image_64")
        private String image64;
        @SerializedName("image_72")
        private String image72;
        @SerializedName("image_96")
        private String image96;
        @SerializedName("image_128")
        private String image128;
        @SerializedName("image_192")
        private String image192;
        @SerializedName("image_512")
        private String image512;
        @SerializedName("image_1024")
        private String image1024;
    }

}
