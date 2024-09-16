package com.slack.api.model.assistant;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class AssistantThreadContext {
    private String enterpriseId;
    private String teamId;
    private String channelId;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId", this.enterpriseId);
        map.put("teamId", this.teamId);
        map.put("channelId", this.channelId);
        return map;
    }
}
