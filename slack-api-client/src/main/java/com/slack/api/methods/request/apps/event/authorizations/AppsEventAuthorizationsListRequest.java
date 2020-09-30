package com.slack.api.methods.request.apps.event.authorizations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/apps.event.authorizations.list
 */
@Data
@Builder
public class AppsEventAuthorizationsListRequest implements SlackApiRequest {

    private String token;

    /**
     * You'll receive an event_context identifying an event in each event payload sent to your app.
     *
     * https://api.slack.com/events-api#begin
     */
    private String eventContext;

    private String cursor;

    private Integer limit;

}