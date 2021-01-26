package com.slack.api.model;

import lombok.Data;

@Data
public class AppStep {

    private String appId;
    private String workflowStepId;
    private String callbackId;
}
