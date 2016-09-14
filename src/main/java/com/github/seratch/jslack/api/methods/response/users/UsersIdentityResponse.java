package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UsersIdentityResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private User user;
    private Team team;

    @Data
    public static class User {
        private String name;
        private String id;
        private String email;
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

    @Data
    public static class Team {
        private String name;
        private String id;
    }
}
