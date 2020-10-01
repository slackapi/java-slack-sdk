package com.slack.api.app_backend.events.payload;

import lombok.Data;

@Data
public class Authorization {
    private String enterpriseId;
    private String teamId;
    private String userId;
    private Boolean isBot;
    private Boolean isEnterpriseInstall;
}
