package com.slack.api.model.event;

import lombok.Data;

/**
 * The team_pref_change event is sent to all connections for a workspace when a preference is changed.
 * Where appropriate clients should update to reflect new changes immediately.
 * <p>
 * https://docs.slack.dev/reference/events/team_pref_change
 */
@Data
public class TeamPrefChangeEvent implements Event {

    public static final String TYPE_NAME = "team_pref_change";

    private final String type = TYPE_NAME;
    private String name;
    private String value;

}