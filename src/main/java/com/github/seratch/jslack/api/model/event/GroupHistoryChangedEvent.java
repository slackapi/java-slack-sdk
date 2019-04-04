package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * A group_history_changed event is sent to all clients in a private channel when bulk changes have occurred to that group's history.
 * When clients receive this message they should reload chat history for the private channel
 * if they have any cached messages before latest.
 * <p>
 * https://api.slack.com/events/group_history_changed
 */
@Data
public class GroupHistoryChangedEvent implements Event {

    public static final String TYPE_NAME = "group_history_changed";

    private final String type = TYPE_NAME;
    private String latest;
    private String ts;
    private String eventTs;

}