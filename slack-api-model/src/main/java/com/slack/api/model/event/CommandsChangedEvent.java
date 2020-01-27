package com.slack.api.model.event;

import lombok.Data;

/**
 * The commands_changed event is sent to all connections for a workspace when a slash command for that workspace is added, removed or changed.
 * <p>
 * This functionality is only used by our web client.
 * The other APIs required to support slash command metadata are currently unstable.
 * Until they are released other clients should ignore this event.
 * <p>
 * https://api.slack.com/events/commands_changed
 */
@Data
public class CommandsChangedEvent implements Event {

    public static final String TYPE_NAME = "commands_changed";

    private final String type = TYPE_NAME;
    private String eventTs;
}