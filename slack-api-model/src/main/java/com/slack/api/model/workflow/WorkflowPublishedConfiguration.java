package com.slack.api.model.workflow;

import com.slack.api.model.AppStep;
import lombok.Data;

import java.util.List;

@Data
public class WorkflowPublishedConfiguration {

    private String versionId;
    private List<AppStep> appSteps;
}
