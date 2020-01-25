package com.slack.api.app_backend.outgoing_webhooks.payload;

import lombok.Data;

@Data
public class WebhookPayload {
    private String token;
    private String teamId;
    private String teamDomain;
    private String serviceId;
    private String channelId;
    private String channelName;
    private String timestamp;
    private String userId;
    private String userName;
    private String text;
    private String triggerWord;
}