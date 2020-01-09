package com.github.seratch.jslack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class RestrictedApp {
    private App app;
    private List<AppScope> scopes;
    private Integer dateUpdated;
    private Actor lastResolvedBy;

    @Data
    public static class Actor {
        private String actorId; // user id
        private String actorType;
    }
}
