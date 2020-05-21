package com.slack.api.model.event;

import lombok.Data;

/**
 * If a Call has been shared with a user in DM
 * (and unfurled with the help of the link_shared event,
 * this event is sent if the user rejects the Call.
 * <p>
 * https://api.slack.com/events/call_rejected
 */
@Data
public class CallRejectedEvent implements Event {

    public static final String TYPE_NAME = "call_rejected";

    private final String type = TYPE_NAME;
    private String callId;
    private String userId;
    private String channelId;
    private String externalUniqueId;

}