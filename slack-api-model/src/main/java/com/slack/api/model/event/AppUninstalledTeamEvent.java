package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/app_uninstalled_team
 */
@Data
public class AppUninstalledTeamEvent implements Event {

    public static final String TYPE_NAME = "app_uninstalled_team";

    private final String type = TYPE_NAME;
    private String appId;
    private String appName;
    private String appOwnerId;
    private String user_id;
    private String teamId;
    private String teamDomain;
    private String eventTs;
}