package com.slack.api.model;

import lombok.Data;

@Data
public class IntegrationLog {
    private String appType;
    private String appId;

    private String serviceId;
    private String serviceType;

    private String userId;
    private String userName;

    private String channel;

    private Integer date;
    private String changeType;
    private String reason;
    private String scope;

    private boolean rssFeed;
    private String rssFeedChangeType;
    private String rssFeedTitle;
    private String rssFeedUrl;

}
