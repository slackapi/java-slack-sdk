package com.github.seratch.jslack.api.status.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class LegacyCurrentStatus {
    private Integer id;
    private String dateCreated;
    private String dateUpdated;
    private String title;
    private String type;
    private String status;
    private String url;
    private List<String> services;
    private List<LegacySlackIssue.Note> notes;

    @Data
    public static class Note {
        private String dateCreated;
        private String body;
    }
}
