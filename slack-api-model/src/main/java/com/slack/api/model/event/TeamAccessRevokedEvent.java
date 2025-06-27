package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/team_access_revoked
 */
@Data
public class TeamAccessRevokedEvent implements Event {

    public static final String TYPE_NAME = "team_access_revoked";

    private final String type = TYPE_NAME;
    private List<String> teamIds; // teams removed, up to 1000 teams
    private String eventTs;
}