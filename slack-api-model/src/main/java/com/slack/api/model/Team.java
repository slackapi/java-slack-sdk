package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Team {

    private String id;
    private String name;
    private String domain;
    private String emailDomain;
    private TeamIcon icon;
    private String url;
    private String publicUrl;

    private String enterpriseId;
    private String enterpriseName;
    private String enterpriseDomain;
    private List<String> defaultChannels;
    private Boolean isVerified;
    private String discoverable; // "invite_only"
    private String avatarBaseUrl;
    private Boolean lobSalesHomeEnabled;
    private Boolean isSfdcAutoSlack;
    private SsoProvider ssoProvider;
    private String payProdCur;

    @Data
    public static class Profile {
        private String id;
        private String sectionId;
        private String fieldName;
        private Integer ordering;
        private String label;
        private String hint;
        private String type;
        private List<String> possibleValues;
        private ProfileOptions options;
        @SerializedName("is_hidden")
        private boolean hidden;
        @SerializedName("is_inverse")
        private boolean inverse;
        private ProfilePermissions permissions;
    }

    @Data
    public static class ProfileOptions {
        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        @SerializedName("is_protected")
        private boolean _protected;

        @SerializedName("is_scim")
        private boolean scim;

        public boolean isProtected() {
            return _protected;
        }

        public void setProtected(boolean isProtected) {
            this._protected = isProtected;
        }
    }

    @Data
    public static class ProfilePermissions {
        private List<String> api;
        private boolean ui;
        private boolean scim;
    }

    @Data
    public static class SsoProvider {
        private String type;
        private String name;
        private String label;
    }
}
