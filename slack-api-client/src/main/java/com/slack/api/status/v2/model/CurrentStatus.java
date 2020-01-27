package com.slack.api.status.v2.model;

import lombok.Data;

import java.util.List;

@Data
public class CurrentStatus {
    private String status;
    private String dateCreated;
    private String dateUpdated;
    private List<SlackIssue> activeIncidents;
}
