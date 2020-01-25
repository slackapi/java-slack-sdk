package com.slack.api.model.event;

import lombok.Data;

/**
 * The manual_presence_change event is sent to all connections for a user when that user manually updates their presence.
 * Clients can use this to update their local state.
 * <p>
 * https://api.slack.com/events/manual_presence_change
 */
@Data
public class ManualPresenceChangeEvent implements Event {

    public static final String TYPE_NAME = "manual_presence_change";

    private final String type = TYPE_NAME;
    private String presence;

}