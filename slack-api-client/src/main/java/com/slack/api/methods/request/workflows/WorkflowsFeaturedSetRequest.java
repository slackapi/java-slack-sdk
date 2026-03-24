package com.slack.api.methods.request.workflows;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/workflows.featured.set/
 */
@Data
@Builder
public class WorkflowsFeaturedSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Channel to set featured workflows in.
     */
    private String channelId;

    /**
     * Comma-separated array of trigger IDs that will replace any existing featured workflows in the channel; max 15
     */
    private List<String> triggerIds;
}
