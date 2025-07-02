package com.slack.api.model.event;

import lombok.Data;

/**
 * The team_rename event is sent to all connections for a workspace when an admin changes the workspace name.
 * <p>
 * Clients can use this to update the display of the workspace name as soon as it changes.
 * If they don't the client will receive the new name the next time it calls rtm.start.
 * <p>
 * https://docs.slack.dev/reference/events/team_rename
 */
@Data
public class TeamRenameEvent implements Event {

    public static final String TYPE_NAME = "team_rename";

    private final String type = TYPE_NAME;
    private String name;

}