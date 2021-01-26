package com.slack.api.model.workflow;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowDraftConfiguration {

    private String versionId;
    private List<AppStep> appSteps;
}
