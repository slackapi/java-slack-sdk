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

    private String enterpriseId;
    private String enterpriseName;
    private List<String> defaultChannels;
    private Boolean isVerified;

    @Data
    public static class Profile {
        private String id;
        private String fieldName;
        private Integer ordering;
        private String label;
        private String hint;
        private String type;
        private List<String> possibleValues;
        private ProfileOptions options;
        @SerializedName("is_hidden")
        private boolean hidden;
    }

    @Data
    public static class ProfileOptions {
        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        @SerializedName("is_protected")
        private boolean _protected;

        public boolean isProtected() {
            return _protected;
        }

        public void setProtected(boolean isProtected) {
            this._protected = isProtected;
        }
    }
}
