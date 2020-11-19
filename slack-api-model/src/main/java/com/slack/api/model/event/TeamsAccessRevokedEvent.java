package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/teams_access_revoked
 */
@Data
public class TeamsAccessRevokedEvent implements Event {

    public static final String TYPE_NAME = "teams_access_revoked";

    private final String type = TYPE_NAME;
    private List<String> teamIds; // teams removed, up to 1000 teams

}