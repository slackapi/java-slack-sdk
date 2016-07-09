package com.github.seratch.jslack.api.model;

import lombok.Data;

@Data
public class IntegrationLog {

    private String serviceId;
    private String serviceType;
    private String userId;
    private String userName;
    private String channel;
    private Integer date;
    private String changeType;
    private String scope;
}
