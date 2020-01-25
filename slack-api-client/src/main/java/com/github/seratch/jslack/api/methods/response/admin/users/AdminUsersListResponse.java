package com.github.seratch.jslack.api.methods.response.admin.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.ResponseMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsersListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<User> users;
    private ResponseMetadata responseMetadata;

    @Data
    public static class User {
        private String id;
        private String email;
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
        private Long expirationTs;
    }
}