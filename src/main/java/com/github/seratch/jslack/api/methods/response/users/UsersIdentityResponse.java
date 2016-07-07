package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
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
    }

    @Data
    public static class Team {
        private String id;
    }
}