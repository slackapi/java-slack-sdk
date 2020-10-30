package com.slack.api.methods.response.admin.users;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsersListResponse implements SlackApiTextResponse {

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