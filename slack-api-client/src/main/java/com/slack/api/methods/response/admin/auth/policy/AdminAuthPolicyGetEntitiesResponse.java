package com.slack.api.methods.response.admin.auth.policy;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAuthPolicyGetEntitiesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Entity> entities;
    private Integer entityTotalCount;

    private ResponseMetadata responseMetadata;

    @Data
    public static class Entity {
        private String entityType;
        private String entityId;
        private Integer dateAdded;
    }
}