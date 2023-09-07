package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.apps.activities.list
 */
@Data
@Builder
public class AdminAppsActivitiesListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String appId;
    private String componentId;
    private String componentType;
    private String cursor;
    private Integer limit;
    private String logEventType;
    private Integer maxDateCreated;
    private Integer minDateCreated;
    private String minLogLevel;
    private String sortDirection;
    private String source;
    private String teamId;
    private String traceId;
}
