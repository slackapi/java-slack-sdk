package com.slack.api.app_backend.dialogs.payload;

import lombok.Data;

@Data
public class Team {
    private String id;
    private String domain;
    private String enterpriseId;
    private String enterpriseName;
}