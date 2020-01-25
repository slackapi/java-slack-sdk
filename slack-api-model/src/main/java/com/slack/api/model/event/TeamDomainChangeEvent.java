package com.slack.api.model.event;

import lombok.Data;

/**
 * The team_domain_change event is sent to all connections for a workspace when the workspace domain changes.
 * <p>
 * Since the existing domain will continue to work (causing a redirect) until it is claimed by another workspace,
 * clients don't need to do anything special with this event. It is sent for the benefit of our web client,
 * which needs to reload when the domain changes.
 * <p>
 * https://api.slack.com/events/team_domain_change
 */
@Data
public class TeamDomainChangeEvent implements Event {

    public static final String TYPE_NAME = "team_domain_change";

    private final String type = TYPE_NAME;
    private String url;
    private String domain;

}