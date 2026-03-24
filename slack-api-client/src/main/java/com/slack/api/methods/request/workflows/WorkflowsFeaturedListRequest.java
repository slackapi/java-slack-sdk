package com.slack.api.methods.request.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/workflows.featured.list/
 */
@Data
@Builder
public class WorkflowsFeaturedListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Comma-separated array of channel IDs to list featured workflows for.
     */
    private List<String> channelIds;

}
