package com.github.seratch.jslack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class AppRequest {
    private String id;
    private App app;
    private User user;
    private Team team;
    private List<AppScope> scopes;
    private PreviousResolution previousResolution;
    private String message;
    private Integer dateCreated;

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
    public static class PreviousResolution {
        private String status;
        private List<AppScope> scopes;
    }

}
