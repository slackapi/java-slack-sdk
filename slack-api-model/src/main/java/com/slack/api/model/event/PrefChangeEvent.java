package com.slack.api.model.event;

import lombok.Data;

/**
 * The pref_change event is sent to all connections for a user when a user preference is changed.
 * Clients should update to reflect new changes immediately.
 * <p>
 * https://api.slack.com/events/pref_change
 */
@Data
public class PrefChangeEvent implements Event {

    public static final String TYPE_NAME = "pref_change";

    private final String type = TYPE_NAME;
    private String name;
    private String value;

}