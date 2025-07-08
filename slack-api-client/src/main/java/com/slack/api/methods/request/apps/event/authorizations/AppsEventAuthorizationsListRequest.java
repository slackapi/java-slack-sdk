package com.slack.api.methods.request.apps.event.authorizations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/apps.event.authorizations.list
 */
@Data
@Builder
public class AppsEventAuthorizationsListRequest implements SlackApiRequest {

    private String token;

    /**
     * You'll receive an event_context identifying an event in each event payload sent to your app.
     * <p>
     * https://docs.slack.dev/apis/events-api
     */
    private String eventContext;

    private String cursor;

    private Integer limit;

}