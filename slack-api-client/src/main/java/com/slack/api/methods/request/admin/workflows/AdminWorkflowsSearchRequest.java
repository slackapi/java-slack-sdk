package com.slack.api.methods.request.admin.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.workflows.search
 */
@Data
@Builder
public class AdminWorkflowsSearchRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String appId;
    private List<String> collaboratorIds;
    private String cursor;
    private Integer limit;
    private Boolean noCollaborators;
    private Integer numTriggerIds;
    private String query;
    private String sort;
    private String sortDir;
    private String source;
}
