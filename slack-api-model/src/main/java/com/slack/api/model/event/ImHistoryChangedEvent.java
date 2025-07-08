package com.slack.api.model.event;

import lombok.Data;

/**
 * An im_history_changed event is sent to all clients in a DM channel When bulk changes have occurred to that DM channel's history.
 * When clients receive this message they should reload chat history for the channel if they have any cached messages before latest.
 * <p>
 * These bulk changes may be the result of data importation or bulk action taken by an administrator.
 * <p>
 * https://docs.slack.dev/reference/events/im_history_changed
 */
@Data
public class ImHistoryChangedEvent implements Event {

    public static final String TYPE_NAME = "im_history_changed";

    private final String type = TYPE_NAME;
    private String latest;
    private String ts;
    private String eventTs;

}
