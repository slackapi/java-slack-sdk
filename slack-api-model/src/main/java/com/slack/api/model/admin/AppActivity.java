package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;
import java.util.Map;

// See https://docs.slack.dev/reference/methods/admin.apps.activities.list
@Data
public class AppActivity {
    private String enterpriseId;
    private String teamId;
    private String appId; // "A05PNMVJEMT" etc.
    private String level; // "info", "error", etc.
    private String eventType; // "function_execution_started", "function_execution_output", "workflow_step_started" etc.
    private String source; // "slack" etc.
    private String componentType; // "functions", "workflows" etc.
    private String parentExecutionId;
    private String componentId;
    private Payload payload;
    private Long created;
    private String traceId; // "Tr05PK0EGXAS" etc.

    @Data
    public static class Payload {
        private String execOutcome; // "Success", "Error" etc.
        private String workflowName;
        private String type;
        private String actor;
        private String channelId;
        private String botUserId;
        private String error; // "restricted_action" etc.
        private String errorExtended;
        private String functionId; // "Fn0106" etc.
        private String functionName; // "Channel Creation" etc.
        private String functionType; // "Slack", "workflow" etc.
        private String functionExecutionId; // "Fx05QMK3RFEY" etc.
        private Integer totalSteps;
        private Integer currentStep;
        private String log;
        private List<String> billingReason; // "uses_custom_function" etc.
        private Boolean isBillingExcluded;
        private Trigger trigger;
        // TODO: complete typing for the value
        private Map<String, Object> inputs;

        private String action;
        private String teamId;
        private String userId;
        private Integer bundleSizeKb;

        // type: datastore_request_result
        private String details; // "{\"expression\":\"\",\"expression_attributes\":{},\"expression_values\":{},\"limit\":1000,\"cursor\":\"\"}"
        private String requestType; // "query"
        private String datastoreName;
    }

    @Data
    public static class Trigger {
        private String id;
        private String type;
        private TriggerConfig config;
        private TripInformation tripInformation;
    }

    @Data
    public static class TriggerConfig {
        private String eventType;
        private String name;
        private String description;
        private Object schema; // TODO: typing
    }

    @Data
    public static class TripInformation {
        private String userId;
        private String channelId;
        private String messageTs;
        private String reaction;
        private String listId;
        // TODO: more properties
    }
}
