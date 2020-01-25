package com.slack.api.model.event;

import lombok.Data;

/**
 * A channel_history_changed event is sent to all clients in a channel when bulk changes have occurred to that channel's history.
 * When clients receive this message they should reload chat history for the channel if they have any cached messages before latest.
 * <p>
 * This message is most often triggered as the result of a channel data import by a workspace administrator.
 * <p>
 * https://api.slack.com/events/channel_history_changed
 */
@Data
public class ChannelHistoryChangedEvent implements Event {

    public static final String TYPE_NAME = "channel_history_changed";

    private final String type = TYPE_NAME;
    private String latest;
    private String ts;
    private String eventTs;
}