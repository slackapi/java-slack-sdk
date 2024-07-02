package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/app_installed
 */
@Data
public class AppInstalledEvent implements Event {

    public static final String TYPE_NAME = "app_installed";

    private final String type = TYPE_NAME;
    private String appId;
    private String appName;
    private String appOwnerId;
    private String user_id;
    private String teamId;
    private String teamDomain;
    private String eventTs;
}