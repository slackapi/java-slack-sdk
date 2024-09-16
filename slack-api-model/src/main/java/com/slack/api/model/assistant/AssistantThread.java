package com.slack.api.model.assistant;

import lombok.Data;

@Data
public class AssistantThread {
    private String userId;
    private AssistantThreadContext context;
    private String channelId;
    private String threadTs;
}
