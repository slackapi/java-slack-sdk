package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/app_deleted
 */
@Data
public class AppDeletedEvent implements Event {

    public static final String TYPE_NAME = "app_deleted";

    private final String type = TYPE_NAME;
    private String appId;
    private String appName;
    private String appOwnerId;
    private String teamId;
    private String teamDomain;
    private String eventTs;
}