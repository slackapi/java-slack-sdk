package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/team_access_granted
 */
@Data
public class TeamAccessGrantedEvent implements Event {

    public static final String TYPE_NAME = "team_access_granted";

    private final String type = TYPE_NAME;
    private List<String> teamIds; // teams added, up to 1000 teams
    private String eventTs;
}