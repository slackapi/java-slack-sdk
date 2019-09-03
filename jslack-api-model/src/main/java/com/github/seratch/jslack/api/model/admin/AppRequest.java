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

}
