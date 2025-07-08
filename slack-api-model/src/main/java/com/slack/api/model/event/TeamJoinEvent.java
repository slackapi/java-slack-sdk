package com.slack.api.model.event;

import com.slack.api.model.User;
import lombok.Data;

/**
 * The team_join event is sent to all connections for a workspace when a new member joins.
 * Clients can use this to update their local cache of members.
 * <p>
 * https://docs.slack.dev/reference/events/team_join
 */
@Data
public class TeamJoinEvent implements Event {

    public static final String TYPE_NAME = "team_join";

    private final String type = TYPE_NAME;
    private User user; // TODO: make sure the available attributes

}