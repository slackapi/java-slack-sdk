package com.slack.api.app_backend.dialogs.payload;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String teamId;
}