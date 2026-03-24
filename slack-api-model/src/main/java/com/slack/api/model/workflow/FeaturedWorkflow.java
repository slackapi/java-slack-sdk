package com.slack.api.model.workflow;

import lombok.Data;

import java.util.List;

@Data
public class FeaturedWorkflow {
    private String channelId;
    private List<WorkflowTrigger> triggers;
}

